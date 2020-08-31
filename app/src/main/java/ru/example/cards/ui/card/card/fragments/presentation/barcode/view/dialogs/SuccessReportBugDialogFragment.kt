package ru.example.cards.ui.card.card.fragments.presentation.barcode.view.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.example.cards.R

class SuccessReportBugDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity!!, R.style.ReportBugAlertDialogStyle)

        builder
            .setTitle(getString(R.string.report_bug_dialog_title_success))
            .setPositiveButton(getString(R.string.ok_action)) { _, _ ->
            }

        return builder.create()
    }
}