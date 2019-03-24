@file:Suppress("DIFFERENT_NAMES_FOR_THE_SAME_PARAMETER_IN_SUPERTYPES")

package io.someapp.wisecontlol.ui.screen.smssender

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.github.florent37.runtimepermission.kotlin.askPermission
import io.someapp.wisecontlol.R
import io.someapp.wisecontlol.data.tasks.TaskFullEntity
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.ui.core.BaseFragment
import io.someapp.wisecontlol.ui.core.withParam
import kotlinx.android.synthetic.main.screen_smssender_view.*
import ru.terrakok.cicerone.android.support.SupportAppScreen


@FragmentScope
class SmsSenderFragment : BaseFragment<SmsSenderPresenter>(), SmsSenderView {

    companion object {
        private const val REQUEST_CODE_CONTACTS = 12313
    }

    @InjectPresenter
    override lateinit var presenter: SmsSenderPresenter

    @ProvidePresenter
    override fun providePresenter(): SmsSenderPresenter = super.providePresenter()

    override fun getLayoutId() = R.layout.screen_smssender_view

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        send.setOnClickListener {
            askPermission(Manifest.permission.SEND_SMS) {
                presenter.sendSms(address.text.toString(), message.text.toString())
            }.onDeclined { e ->
                e.goToSettings()
            }
        }
        choose_contact.setOnClickListener { chooseContact() }
    }

    private fun chooseContact() {
        askPermission(Manifest.permission.READ_CONTACTS) {

            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
//        val intent = Intent(Intent.ACTION_PICK)
            intent.type = ContactsContract.Contacts.CONTENT_TYPE
            startActivityForResult(intent, REQUEST_CODE_CONTACTS)
        }.onDeclined { e ->
            e.goToSettings()
        }

    }

    override fun updateUI(task: TaskFullEntity) {
        val task1 = task.task
        val mes: String =
            "${task1.title}${task.category?.let { "(${it.title})\n" }}\n${task1.description}\n${task1.startDate.toString()}"
        message.setText(mes)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (REQUEST_CODE_CONTACTS == requestCode && data != null) {
            val uri = data.data ?: return
            val query = activity?.contentResolver
                ?.query(
                    uri,
                    null, null, null, null
                )
            query
                .use {
                    if (it?.moveToFirst() == true) {
                        val contactID = it.getString(it.getColumnIndex(ContactsContract.Contacts._ID));

                        activity?.contentResolver?.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactID,
                            null,
                            null
                        )
                            .use {
                                if (it?.moveToFirst() == true) {
                                    val number =
                                        it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    address.setText(number)
                                }
                            }

//                        val number = it.getString(0)
//                        address.setText(number)
                    }
                }
        }
    }
}

@StateStrategyType(value = SkipStrategy::class)
interface SmsSenderView : MvpView {
    fun updateUI(task: TaskFullEntity)
    fun showToast(textId: Int)
}


class SmsSenderScreen(
    private val taskId: Long
) : SupportAppScreen() {
    companion object {
        const val KEY_ID: String = "KEY_ID"
    }

    override fun getFragment() = SmsSenderFragment().withParam {
        putLong(KEY_ID, taskId)
    }
}