package com.nalexand.friendlocation.view

/*
fun 	startRemoveUserView(activity: MainActivity, item: UserEntity) {

    val alertBuilder = AlertDialog.Builder(activity)
    val dialogView = LayoutInflater.from(activity).inflate(R.layout.window_user_remove, null)
    val dialog = alertBuilder.setView(dialogView).create()
    val delBtn = dialogView.findViewById<Button>(R.id.delUserBtn)

    delBtn.setOnClickListener {
        activity.db.make().delete(item)
        (activity.myRecycler.adapter as ViewAdapter).updateData(activity.db.make().getAll())
        Toast.makeText(activity, "User deleted", Toast.LENGTH_SHORT).show()
        if (activity.db.make().getCount() == 0)
            activity.findViewById<TextView>(R.id.start).visibility = View.VISIBLE
        dialog.dismiss()
    }
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.show()
}
*/