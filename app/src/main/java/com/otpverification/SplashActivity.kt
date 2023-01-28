package  com.otpverification;


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.app_preference_section.AppPreference
import com.base_class_section.BaseActivity


class SplashActivity : BaseActivity() {
    /** Duration of wait  */
    private val SPLASH_DISPLAY_LENGTH = 1000
    private var mAppPreference: AppPreference? = null

    /** Called when the activity is first created.  */
    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_splash)
        mAppPreference = AppPreference(this)


        moveToNextScreen()
    }


    override fun onResume() {
        super.onResume()
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String?) {
    }





    fun moveToNextScreen(){
        if(isNetworkConnected){
            Handler().postDelayed({ /* Create an Intent that will start the Menu-Activity. */
                if (mAppPreference!!.isLoginDone) {
                    val mainIntent = Intent(this@SplashActivity, HomeActivity::class.java)
                    this@SplashActivity.startActivity(mainIntent)
                    finish()
                } else {
                    val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
                    this@SplashActivity.startActivity(mainIntent)
                    finish()
                }
            }, (2 * 1000).toLong())
        }
        else{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Internet Connection")
            builder.setMessage("Please connect with internet and try again")

            builder.setPositiveButton("Ok") { dialog, which ->
                finish()
            }
            builder.show()
        }

    }

}