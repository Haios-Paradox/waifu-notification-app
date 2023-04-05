package com.example.waifunotificationapp.model
import com.example.waifunotificationapp.model.Utility.promptBuilder
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class Repository {
    val db: FirebaseFirestore = Firebase.firestore
    val auth = FirebaseAuth.getInstance()
    val uid : String? = auth.uid
    val usersCollection = db.collection(Collections.USERS)
    val waifusCollection = db.collection(Collections.WAIFU)
    val messagesCollection = db.collection(Collections.MESSAGES)
    val requestsCollection = db.collection(Collections.REQUESTS)

    suspend fun login(email: String, password: String): FirebaseUser {
        val authResult = auth.signInWithEmailAndPassword(email, password).await()
        return authResult.user ?: throw Exception("Login failed. User is null.")
    }

    suspend fun register(email: String, password: String): FirebaseUser {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        return authResult.user ?: throw Exception("Registration failed. User is null.")
    }

    fun logout() {
        auth.signOut()
    }


    fun getAllWaifus(
        onSuccess: (List<DocumentSnapshot>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        if (uid == null) {
            // User is not signed in, handle this case as needed
            return
        }
        // Retrieve the user document and get the list of character IDs
        usersCollection.document(uid).get().addOnSuccessListener { userDoc ->
            val waifusIds = userDoc.get("waifus") as? List<String> ?: emptyList()

            // Retrieve the character documents using the list of IDs
            val waifuDocs = mutableListOf<DocumentSnapshot>()
            waifusIds.forEach { waifuId ->
                val waifuRef = waifusCollection.document(waifuId)
                waifuRef.get().addOnSuccessListener{waifuDoc ->
                    waifuDocs.add(waifuDoc)
                    if(waifuDocs.isNotEmpty())
                        onSuccess(waifuDocs)
                    else
                        onFailure(java.lang.Exception())
                }.addOnFailureListener {error ->
                    onFailure(error)
                }

            }
        }.addOnFailureListener { error ->
            onFailure(error)
        }
    }

    fun getAllMessages(
        callback: (List<WaifuMessage>) -> Unit
    ) {
        getWaifuIds(onSuccess = {characterIds->
            messagesCollection.whereEqualTo("userId", uid)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val messagesWithCharacters = mutableListOf<WaifuMessage>()
                    for (document in querySnapshot.documents) {
                        val message = document.toObject(Message::class.java)
                        val waifuId = message?.waifuId ?: ""
                        if (waifuId in characterIds) {
                            val waifuRef = waifusCollection.document(waifuId)
                            waifuRef.get().addOnSuccessListener { waifuDoc ->
                                val waifu = waifuDoc.toObject(Waifu::class.java)
                                if (waifu != null) {
                                    val waifuMessage = WaifuMessage(message!!, waifu)
                                    messagesWithCharacters.add(waifuMessage)
                                }
                                if (messagesWithCharacters.size == querySnapshot.documents.size) {
                                    callback(messagesWithCharacters)
                                }
                            }
                        } else {
                            if (messagesWithCharacters.size == querySnapshot.documents.size) {
                                callback(messagesWithCharacters)
                            }
                        }
                    }
                }
                .addOnFailureListener { e ->
                    // Handle any errors that occur
                }
        },
        onFailure = {
            //do nothing
        })

    }

    fun getUserData(
        onSuccess: (DocumentSnapshot) -> Unit,
        onFailure: (Exception) -> Unit
    ){
        if (uid != null) {
            usersCollection.document(uid).get().addOnSuccessListener {
                onSuccess(it)
            }.addOnFailureListener{
                onFailure(it)
            }
        }
    }

    fun getAllTask(
        onSuccess: (List<DocumentSnapshot>) -> Unit,
        onFailure: (Exception) -> Unit
    ){
    }

    fun promptRandomCharacterWithTask(
        task: Task? = null
    ) {
        if(uid!=null){
            usersCollection.document(uid).get().addOnSuccessListener {userDoc->
                val user = userDoc.toObject<User>()
                val waifuIds = user?.waifus
                val randomWaifu = waifuIds?.random()
                if(randomWaifu!=null)
                    waifusCollection.document(randomWaifu).get()
                        .addOnSuccessListener { characterDoc ->
                            val waifu = characterDoc.toObject(Waifu::class.java)
                            if (waifu != null) {
                                promptBuilder(waifu,user) { userInput ->
                                    createRequest(randomWaifu,userInput)
                                }
                            }
                        }
                        .addOnFailureListener { exception ->
                            // Handle error
                        }
            }
        }
    }


    fun createWaifu(waifu: Waifu) {
        if (uid == null) {
            // User is not signed in, handle this case as needed
            return
        }

        // Create a new character document
        val newCharacterDoc = waifusCollection.document()
        newCharacterDoc.set(waifu)

        // Update the current user document with the new character ID
        usersCollection.document(uid).update("waifus", FieldValue.arrayUnion(newCharacterDoc.id))
    }



    fun createRequest(characterId: String, message: String, task: Task? = null) {
        val timestamp = Timestamp.now()
        val request = hashMapOf(
            "message" to message,
            "timestamp" to timestamp,
            "userId" to uid,
            "characterId" to characterId
        )
        if (task != null) {
            request["task"] = task.description
        }
        requestsCollection
            .add(request)
            .addOnSuccessListener {
                // Request was successfully added to Firestore
            }
            .addOnFailureListener { e ->
                // Handle any errors that occur
            }
    }


    fun getWaifuIds(
        onSuccess: (List<String>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        if (uid == null) {
            // User is not signed in, handle this case as needed
            onFailure(Exception("User is not signed in"))
            return
        }
        val userRef = usersCollection.document(uid)
        userRef.get().addOnSuccessListener { userDoc ->
            val waifuIds = userDoc.get(Collections.WAIFU) as? List<String> ?: emptyList()
            onSuccess(waifuIds)
        }.addOnFailureListener { error ->
            onFailure(error)
        }
    }
}