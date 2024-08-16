package br.edu.ifsp.arq.dmos5.brotinho.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import br.edu.ifsp.arq.dmos5.brotinho.ActivityRegisterActivity
import br.edu.ifsp.arq.dmos5.brotinho.MyActivitiesListActivity
import br.edu.ifsp.arq.dmos5.brotinho.R
import br.edu.ifsp.arq.dmos5.brotinho.model.PhysicalActivity
import java.lang.String

class ActivityAdapter(
    context: Context,
    textViewResourceId: Int,
    private val activities: List<PhysicalActivity>
) :
    ArrayAdapter<PhysicalActivity?>(context, textViewResourceId, activities) {
    private val context: Context

    init {
        this.context = context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v: View? = convertView
        if (v == null) {
            val inflater = getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            v = inflater.inflate(R.layout.activity_list_layout, null)
        }
        val activity = activities[position] ?: return v!!
        val type = v?.findViewById(R.id.activity_type) as TextView
        val date = v.findViewById(R.id.activity_date) as TextView
        val distance = v.findViewById(R.id.activity_distance) as TextView
        val duration = v.findViewById(R.id.activity_duration) as TextView
        val btnEdt = v.findViewById(R.id.btn_edt) as Button
        btnEdt.setOnClickListener { view: View? ->
            edit(
                position
            )
        }
        val btnDel = v.findViewById(R.id.btn_del) as Button
        btnDel.setOnClickListener { view: View? ->
            delete(
                position
            )
        }
        type.setText(activity.type?.value.toString())
        date.setText(activity.date)
        distance.text = String.format("%.3f Km", activity.distance)
        duration.text = String.format("%.1f min", activity.duration)
        return v
    }

    private fun edit(pos: Int) {
        val intent = Intent(
            context,
            ActivityRegisterActivity::class.java
        )
        intent.putExtra("activity", activities[pos])
        context.startActivity(intent)
    }

    private fun delete(pos: Int) {
        val msgBox: AlertDialog.Builder = android.app.AlertDialog.Builder(context)
        msgBox.setTitle("Excluindo...")
        msgBox.setIcon(android.R.drawable.ic_menu_delete)
        msgBox.setMessage("Deseja realmente excluir esta atividade?")
        msgBox.setPositiveButton("SIM",
            DialogInterface.OnClickListener { dialogInterface, i ->
                val myActivitiesListActivity: MyActivitiesListActivity =
                    context as MyActivitiesListActivity
                myActivitiesListActivity.deleteActivity(pos)
            })
        msgBox.setNegativeButton("NÃO",
            DialogInterface.OnClickListener { dialogInterface, i -> })
        msgBox.show()
    }
}