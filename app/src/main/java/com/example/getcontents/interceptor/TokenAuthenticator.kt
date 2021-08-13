import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import com.example.getcontents.App
import com.example.getcontents.App.Companion.sharedPref
import com.example.getcontents.activity.BaseActivity
import com.example.getcontents.activity.LoginActivity
import com.example.getcontents.activity.MainActivity
import com.example.getcontents.extensions.Extensions.startActivityWithFinish
import com.example.getcontents.login.LoginDto
import com.example.getcontents.login.LoginInterface
import com.example.getcontents.login.login_Result
import com.example.getcontents.network.NullOnEmptyConverterFactory
import com.example.getcontents.storage.SharedPref
import com.example.getcontents.token.ReissueTokenInterface
import com.google.gson.Gson
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TokenAuthenticator constructor(val context: Context, private val refreshToken: String) : okhttp3.Authenticator {
    lateinit var httpService: ReissueTokenInterface
    override fun authenticate(route: Route?, response: Response): Request? {
        Log.e("authenticate", "code = ${response.code()}")
         val token = sharedPref.getToken(SharedPref.PREF_KEEP_LOGIN_TOKEN)


        synchronized(this) {
            val newToken = sharedPref.getToken(SharedPref.PREF_KEEP_LOGIN_TOKEN)
            if (response.request().header(AUTHORIZATION) != null) {

                if (token != newToken) {
                    return response.request()
                        .newBuilder()
                        .removeHeader(AUTHORIZATION)
                        .header(AUTHORIZATION, "Bearer $refreshToken")
                        .build()
                }

                val updatedToken =
                    sharedPref.getToken(SharedPref.PREF_REFRESH_LOGIN_TOKEN)
                val res = httpService.reissueToken("Bearer $updatedToken").execute()

                return if (res.code() == SUCCESS) {
                    sharedPref.storedToken(
                        SharedPref.PREF_KEEP_LOGIN_TOKEN,
                        res.body()!!.result!!
                    )
                    val newRequest = response.request().newBuilder()
                        .removeHeader(AUTHORIZATION)
                        .header(
                            AUTHORIZATION,
                            "Bearer ${sharedPref.getToken(SharedPref.PREF_KEEP_LOGIN_TOKEN)}"
                        )
                        .build()

                    newRequest
                }else {
                    sharedPref.forcedLogout(BaseActivity.instance)
                    null
                }
            }
        }
        return null
    }
    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val SUCCESS = 200
        private const val BASE_URL = "https://api.super-brain.co.kr/"
    }

}