package ru.example.cards.ui.card.card.fragments.presentation.barcode.view.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.example.cards.R

class ReportBugDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity!!, R.style.ReportBugAlertDialogStyle)

        builder
            .setTitle(getString(R.string.report_bug_dialog_title_answer))
            .setMessage(getString(R.string.report_bug_dialog_message_answer))
            .setPositiveButton(getString(R.string.report_bug)) { _, _ ->
                SuccessReportBugDialogFragment().show(fragmentManager, "success_report_dialog_fragment")
            }

        return builder.create()
    }
}