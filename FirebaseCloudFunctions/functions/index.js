// const functions = require('firebase-functions');
var functions = require('firebase-functions');
var admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);

exports.notificationOnWrite = functions.database.ref('/users/{user_id}/makul/{makul_id}')
  .onWrite(event => {
    var eventSnapshot = event.data;
    var topic = "makul_on_write";
    var payload = {
      data : {
            nama_makul: eventSnapshot.child("nama_makul").val(),
            dosen: eventSnapshot.child("dosen").val()
      }
    };
    return admin.messaging().sendToTopic(topic, payload)
      .then(function (response) {
        console.log("Pengiriman message sukses : ", response);
      })
      .catch(function (error){
        console.log("Pengiriman message gagal : ", error);
      });
  });

  exports.notificationOnDelete = functions.database.ref('/users/{user_id}/makul/{makul_id}')
    .onDelete(event => {
      var eventSnapshot = event.data;
      var topic = "makul_on_delete";
      var payload = {
        data : {
              nama_makul: eventSnapshot.child("nama_makul").val(),
              dosen: eventSnapshot.child("dosen").val()
        }
      };
      return admin.messaging().sendToTopic(topic, payload)
        .then(function (response) {
          console.log("Pengiriman message sukses : ", response);
        })
        .catch(function (error){
          console.log("Pengiriman message gagal : ", error);
        });
    });

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
