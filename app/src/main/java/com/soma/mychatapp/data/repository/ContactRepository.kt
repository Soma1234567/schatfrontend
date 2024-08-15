package com.soma.mychatapp.data.repository

import android.content.Context
import android.provider.ContactsContract
import com.soma.mychatapp.data.local.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class ContactRepository(
    private val context: Context
) {
    suspend fun getContacts(): List<Contact> = coroutineScope {
        async(Dispatchers.IO) { getContactList() }.await()
    }
    private fun getContactList(): List<Contact> {
        val contactsList = mutableListOf<Contact>()
        val hashset  = mutableSetOf<String>()
        context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        )?.use { contactsCursor ->
            val idIndex = contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIndex = contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex = contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (contactsCursor.moveToNext()) {
                val id = contactsCursor.getString(idIndex)
                val name = contactsCursor.getString(nameIndex)
                val number = contactsCursor.getString(numberIndex)
                if(!hashset.contains(id)){
                    contactsList.add(
                        Contact(
                            name = name, number = number
                        )
                    )
                }
                hashset.add(id)

            }
        }
        return contactsList
    }
}