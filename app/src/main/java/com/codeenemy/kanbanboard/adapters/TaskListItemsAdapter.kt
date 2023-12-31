package com.codeenemy.kanbanboard.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codeenemy.kanbanboard.activities.TaskListActivity
import com.codeenemy.kanbanboard.databinding.ItemTaskBinding
import com.codeenemy.kanbanboard.model.Task

// TODO (Step 5: Create an adapter class for Task List Items in the TaskListActivity.)
// START
open class TaskListItemsAdapter(
    private val context: Context, private var list: ArrayList<Task>
) : RecyclerView.Adapter<TaskListItemsAdapter.ViewHolder>() {

    private var onClickListener: TaskListItemsAdapter.OnClickListener? = null

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    inner class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // TODO (Step 6: Here we have done some additional changes to display the item of the task list item in 70% of the screen size.)
        // START
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // Here the layout params are converted dynamically according to the screen size as width is 70% and height is wrap_content.
        val layoutParams = LinearLayout.LayoutParams(
            (parent.width * 0.7).toInt(), LinearLayout.LayoutParams.WRAP_CONTENT
        )
        // Here the dynamic margins are applied to the view.
        layoutParams.setMargins((15.toDp()).toPx(), 0, (40.toDp()).toPx(), 0)
        binding.root.layoutParams = layoutParams

        return ViewHolder(binding)
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPosition = holder.adapterPosition
        val model = list[position]
        with(holder) {
            with(list[position]) {

                if (position == list.size - 1) {
                    holder.binding.tvAddTaskList.visibility = View.VISIBLE
                    holder.binding.llTaskItem.visibility = View.GONE
                } else {
                    holder.binding.tvAddTaskList.visibility = View.GONE
                    holder.binding.llTaskItem.visibility = View.VISIBLE
                }
                holder.binding.tvTaskListTitle.text = model.title
                holder.binding.tvAddTaskList.setOnClickListener {
                    holder.binding.tvAddTaskList.visibility = View.GONE
                    holder.binding.cvAddTaskListName.visibility = View.VISIBLE
                }
                holder.binding.ibDoneListName.setOnClickListener {
                    val listName = holder.binding.etTaskListName.text.toString()
                    if (listName.isNotEmpty()) {
                        if (context is TaskListActivity) {
                            context.createList(listName)
                        }
                    } else {
                        Toast.makeText(context, "Please Enter List Name.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                holder.binding.ibCloseListName.setOnClickListener {
                    holder.binding.tvAddTaskList.visibility = View.VISIBLE
                    holder.binding.cvAddTaskListName.visibility = View.GONE
                }
                holder.binding.ibEditListName.setOnClickListener {

                    holder.binding.etEditTaskListName.setText(model.title) // Set the
                    // existing title
                    holder.binding.llTitleView.visibility = View.GONE
                    holder.binding.cvEditTaskListName.visibility = View.VISIBLE
                }
                holder.binding.ibCloseEditableView.setOnClickListener {
                    holder.binding.llTitleView.visibility = View.VISIBLE
                    holder.binding.cvEditTaskListName.visibility = View.GONE
                }
                holder.binding.ibDoneEditListName.setOnClickListener {
                    val listName = holder.binding.etEditTaskListName.text.toString()

                    if (listName.isNotEmpty()) {
                        if (context is TaskListActivity) {
                            context.updateTaskList(position, listName, model)
                        }
                    } else {
                        Toast.makeText(context, "Please Enter List Name.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                holder.binding.ibDeleteList.setOnClickListener {
                    alertDialogForDeleteList(position, model.title)
                }

                holder.binding.tvAddCard.setOnClickListener {
                    holder.binding.tvAddCard.visibility = View.GONE
                    holder.binding.cvAddCard.visibility = View.VISIBLE
                }
                holder.binding.ibCloseCardName.setOnClickListener {
                    holder.binding.tvAddCard.visibility = View.VISIBLE
                    holder.binding.cvAddCard.visibility = View.GONE
                }
                holder.binding.ibDoneCardName.setOnClickListener {
                    val cardName = holder.binding.etCardName.text.toString()
                    if (cardName.isNotEmpty()) {
                        if (context is TaskListActivity) {
                            context.addCardToTaskList(holder.adapterPosition, cardName)
                        }
                    } else {
                        Toast.makeText(context, "Please Enter Card Name.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                holder.binding.rvCardList.layoutManager = LinearLayoutManager(context)
                holder.binding.rvCardList.setHasFixedSize(true)
                val adapter = CardListItemsAdapter(context, model.cards)
                holder.binding.rvCardList.adapter = adapter
//                adapter.setOnClickListener(object : CardListItemsAdapter.OnClickListener {
//                    override fun onClick(cardPosition: Int) {
//
//                        if (context is TaskListActivity) {
//                            context.cardDetails(position, cardPosition)
//                        }
//                    }
//                })
            }
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * A function for OnClickListener where the Interface is the expected parameter..
     */
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    /**
     * An interface for onclick items.
     */
    interface OnClickListener {
        fun onClick(position: Int, task: Task)
    }

    /**
     * A function to get density pixel from pixel
     */
    private fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

    /**
     * A function to get pixel from density pixel
     */
    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    /**
     * Method is used to show the Alert Dialog for deleting the task list.
     */
    private fun alertDialogForDeleteList(position: Int, title: String) {
        val builder = AlertDialog.Builder(context)
        //set title for alert dialog
        builder.setTitle("Alert")
        //set message for alert dialog
        builder.setMessage("Are you sure you want to delete $title.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            dialogInterface.dismiss() // Dialog will be dismissed

            if (context is TaskListActivity) {
                context.deleteTaskList(position)
            }
        }

        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which ->
            dialogInterface.dismiss() // Dialog will be dismissed
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false) // Will not allow user to cancel after clicking on remaining screen area.
        alertDialog.show()  // show the dialog to UI
    }
}