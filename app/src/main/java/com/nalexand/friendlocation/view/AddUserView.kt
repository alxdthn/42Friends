package com.nalexand.friendlocation.view

/*
fun startAddUserView(activity: MainActivity) {

    val alertBuilder = AlertDialog.Builder(activity)
    val dialogView = LayoutInflater.from(activity).inflate(R.layout.window_user_add, null)
    val input = dialogView.findViewById<EditText>(R.id.input_text)
    val findBtn = dialogView.findViewById<Button>(R.id.findBtn)
    val dialog = alertBuilder.setView(dialogView).create()

    findBtn.setOnClickListener {
        CoroutineScope(Dispatchers.IO).launch {
            val ret =
                getUser(activity, input.text.toString(), activity.db)
            activity.runOnUiThread {
                when (ret) {
                    0 -> {
                        Toast.makeText(activity, "User added!", Toast.LENGTH_SHORT).show()
                        activity.findViewById<TextView>(R.id.start).visibility = View.INVISIBLE
                        dialog.dismiss()
                    }
                    1 -> Toast.makeText(activity, "User already exist!", Toast.LENGTH_SHORT).show()
                    2 -> Toast.makeText(activity, "No internet connection", Toast.LENGTH_SHORT).show()
                    3 -> Toast.makeText(activity, "Can't find User", Toast.LENGTH_SHORT).show()
                    4 -> Toast.makeText(activity, "Try again, my friend!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.show()
}

*/