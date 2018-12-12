package munchhunt.munchhuntproject.Party;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import munchhunt.munchhuntproject.Callback.BooleanCallback;
import munchhunt.munchhuntproject.Callback.CurrentUserCallback;
import munchhunt.munchhuntproject.Callback.CurrentUserIDCallback;
import munchhunt.munchhuntproject.Callback.DietPatternCallback;
import munchhunt.munchhuntproject.Callback.DietPatternListCallback;
import munchhunt.munchhuntproject.Callback.FriendListCallback;
import munchhunt.munchhuntproject.Callback.PartyCallback;
import munchhunt.munchhuntproject.Callback.StringCallback;
import munchhunt.munchhuntproject.Callback.UserCallback;
import munchhunt.munchhuntproject.Objects.DietPattern;
import munchhunt.munchhuntproject.Objects.Party;
import munchhunt.munchhuntproject.Objects.User;
import munchhunt.munchhuntproject.Profile.FriendAdderAdapter;

public class SocialFirebase {

    private static DatabaseReference userRef =  FirebaseDatabase.getInstance().getReference().child("Users");
    private static DatabaseReference partyRef = FirebaseDatabase.getInstance().getReference().child("Parties");
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private static FirebaseUser mUser;

    private static String userToBeCalledID;
    private static User userToBeCalled;
    private static String partyToBeCalledID;
    private static  boolean flag = false;

    public SocialFirebase() {
    }


    public static void updateUser(User userToBeUpdated) {

        String id = userToBeUpdated.getId();
        userRef.child(id).setValue(userToBeUpdated);
    }


    public static void updateParty(Party partyToBeUpdated) {

        String id = partyToBeUpdated.getId();
        partyRef.child(id).setValue(partyToBeUpdated);
    }


    public static void hasUser(final String userID, @NonNull final BooleanCallback finishedCallback) {

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    User user = ds.getValue(User.class);
                    if (user.getId().equals(userID)) {
                        finishedCallback.callback(true);
                    } else {
                        finishedCallback.callback(false);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    public static void readCurrentUserID(@NonNull final CurrentUserIDCallback finishedCallback){
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    finishedCallback.callback(user.getUid());

                    //4  Toast.makeText(Profile.this, "USER ID\n"+currentUser,Toast.LENGTH_SHORT).show();

                } else {
                    //  Toast.makeText(Profile.this, "USER ID\n"+ currentUser,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public static void callCurrentUser(@NonNull final CurrentUserCallback finishedCallback){
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if (firebaseUser != null) {
                    final String currentUserfID = firebaseUser.getUid();
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                User user = ds.getValue(User.class);
                                if (user.getFirebaseId().equals(currentUserfID)) {
                                    finishedCallback.callback(user);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    //4  Toast.makeText(Profile.this, "USER ID\n"+currentUser,Toast.LENGTH_SHORT).show();

                } else {
                    Log.d("user logged in", "logged in");
                }
            }
        });
    }

    public static void readUser(final String userID, @NonNull final UserCallback<User> finishedCallback) {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    User user = ds.getValue(User.class);
                    if (user.getId().equals(userID)) {
                        finishedCallback.callback(user);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

//    public static void readAllUser(@NonNull final UserListCallback finishedCallback) {
//        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                List<User> userList = new ArrayList<User>();
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//
//                    User user = ds.getValue(User.class);
//                    userList.add(user);
//                }
//                finishedCallback.callback(userList);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//        });
//
//    }

//    public static void readNameContainUser(final String userName, @NonNull final UserListCallback finishedCallback) {
//        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                List<User> userList = new ArrayList<User>();
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//
//                    User user = ds.getValue(User.class);
//                    if(userName.toLowerCase().contains(user.getName().toLowerCase()))
//                    userList.add(user);
//                }
//                finishedCallback.callback(userList);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//        });
//
//    }



    public static void readUserIDbyName(final String userName, @NonNull final StringCallback finishedCallback) {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    User user = ds.getValue(User.class);
                    if (user.getName().equals(userName)) {
                        finishedCallback.callback(user.getId());
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    public static void readUserDietbyID(final String userID, @NonNull final DietPatternCallback finishedCallback) {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    User user = ds.getValue(User.class);
                    if (user.getId().equals(userID)) {
                        finishedCallback.dietPatternCallback(user.getDiet());
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    //When calling for firends list, use readFriends(String id, new FriendsLIstCallback<List>){ implement abstract methods

    public static void readFriends(String userToBeReadID, @NonNull final FriendListCallback finishedCallback) {
        readUser(userToBeReadID, new UserCallback<User>() {
            @Override
            public void callback(User user) {
                finishedCallback.callback(user.getFriendsList());
            }
        });

    }

    public static void autoUpdateFriend(final String userToBeReadID, @NonNull final FriendListCallback finishedCallback){
        ValueEventListener valueEventListener = userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    User user = ds.getValue(User.class);
                    if (user.getId().equals(userToBeReadID)) {
                        finishedCallback.callback(user.getFriendsList());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public static void addFriend(final User currentUser, final String friendID) {
        hasUser(friendID, new BooleanCallback() {
            @Override
            public void callback(boolean data) {
                if (data) {
                    if(!currentUser.getId().equals(friendID)) {
                        currentUser.addFriend(friendID);
                        updateUser(currentUser);
                    }
                }
            }
        });


    }

    public static void addFriendBothWays(final User currentUser, final String friendID) {
        hasUser(friendID, new BooleanCallback() {
            @Override
            public void callback(boolean data) {
                if (data) {
                    if(!currentUser.getId().equals(friendID)) {
                        currentUser.addFriend(friendID);
                        updateUser(currentUser);
                        readUser(friendID, new UserCallback<User>() {
                            @Override
                            public void callback(User user) {
                                user.addFriend(currentUser.getId());
                                updateUser(user);
                            }
                        });

                    }
                }
            }
        });


    }


    public static void removeFriend(final String friendID) {
        callCurrentUser(new CurrentUserCallback() {
            @Override
            public void callback(User currentUser) {
                currentUser.removeFriend(friendID);
                updateUser(currentUser);
            }
        });

    }


    public static void checkMutual(final User currentUser, final String friendID, @NonNull final BooleanCallback finishedCallback){
        readFriends(friendID, new FriendListCallback() {
            @Override
            public void callback(List friendsList) {
                boolean friended1 = false;
                boolean friended2 = false;
                List<String> friendList1 = currentUser.getFriendsList();
                List<String> friendList2 = (List<String>)friendsList;

                for(int i = 0; i < friendList1.size(); i++){
                    if(friendList1.contains(friendID)){
                        friended1 = true;
                        break;
                    }
                }
                for(int i = 0; i < friendList2.size(); i++){
                    if(friendList2.contains(currentUser.getId())){
                        friended2 = true;
                        break;
                    }
                }
                finishedCallback.callback(friended1&&friended2);
            }
        });
    }

    public static void addToParty(final User currentUser, final String friendID){
        checkInParty(friendID, new BooleanCallback() {
            @Override
            public void callback(boolean data) {
                Log.d("is friend in party?", data+"");
                if(!data) {
                    checkInParty(currentUser.getId(), new BooleanCallback() {
                        @Override
                        public void callback(boolean data) {
                            Log.d("is currentUserin party?", data+"");
                            if (!data) {

                                Party party = new Party();
                                if(party.getparty().get(0).equals(""))
                                    party.getparty().clear();
                                party.addToParty(currentUser.getId());
                                party.addToParty(friendID);
                                DatabaseReference newRef = partyRef.push();
                                party.setId(newRef.getKey());
                                newRef.setValue(party);
                            }
                            else {
                                getUserParty(currentUser, new PartyCallback<Party>(){
                                    @Override
                                    public void callback(Party party) {
                                        Log.d("current Party member", party.getparty().toString());
                                        party.getparty().add(friendID);
                                        updateParty(party);
                                    }
                                });
                            }
                        }
                    });
                }

                else {
                }
            }
        });

    }

    public static void leaveFromParty(final String usertoBeRemovedID){

        readUser(usertoBeRemovedID, new UserCallback<User>() {
            @Override
            public void callback(User user) {
                getUserParty(user, new PartyCallback<Party>() {
                    @Override
                    public void callback(Party party) {
                        party.leaveParty(usertoBeRemovedID);
                        updateParty(party);
                    }
                });
            }
        });



    }

    public static void pushEmptyParty(){

        Party party = new Party();
        partyRef.push().setValue(party);
    }

    //always call resetTempValue method after the usage of this method.
    public static void checkInParty(final String userID, @NonNull final BooleanCallback finishedCallback){
        partyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            boolean flag;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                outerloop:
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Party partyFromDatabase = ds.getValue(Party.class);
                    for(int i = 0; i < partyFromDatabase.getparty().size(); i++) {
                        if (partyFromDatabase.getparty().contains(userID)) {
                            flag = true;
                            Log.d("Boolean", flag+"");
                            break outerloop;
                        } else {
                            flag = false;
                        }

                    }
                    Log.d("Boolean", flag+"");
                }

                finishedCallback.callback(flag);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

    }




    public static void getUserParty(final User user, @NonNull final PartyCallback<Party> finishedCallback) {


        partyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                outerloop:
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    GenericTypeIndicator<Party> t = new GenericTypeIndicator<Party>() {
                    };
                    Party partyFromDatabase = ds.getValue(t);

                    if (partyFromDatabase != null) {

                        if (partyFromDatabase.getparty().contains(user.getId())) {
                            finishedCallback.callback(partyFromDatabase);
                            break outerloop;
                        }

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

    }

    public static void autoUpdateUserParty(final User user, @NonNull final PartyCallback<Party> finishedCallback) {


        ValueEventListener valueEventListener = partyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                outerloop:
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    GenericTypeIndicator<Party> t = new GenericTypeIndicator<Party>() {
                    };
                    Party partyFromDatabase = ds.getValue(t);

                    if (partyFromDatabase != null && partyFromDatabase.getparty() !=null) {

                        if (partyFromDatabase.getparty().contains(user.getId())) {
                            finishedCallback.callback(partyFromDatabase);
                            break outerloop;
                        }

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

    }

    public static void getPartyDietPattern(final User user, @NonNull final DietPatternListCallback finishedCallback) {

        autoUpdateUserParty(user, new PartyCallback<Party>() {
            @Override
            public void callback(Party party) {
                Log.d("party members", party.getparty().toString());
                final List<String> partyMembers = party.getparty();
                final List<DietPattern> dietPatterns = new ArrayList<DietPattern>();

                for(int i = 0; i < partyMembers.size(); i++){
                    readUserDietbyID(partyMembers.get(i), new DietPatternCallback() {
                        @Override
                        public void dietPatternCallback(DietPattern dietPattern) {


                            dietPatterns.add(dietPattern);

                        }
                    });
                }

                finishedCallback.dietPatternListCallback(dietPatterns);




            }
        });

    }

    public static void sendFriendRequest(final String receiverID){
        callCurrentUser(new CurrentUserCallback() {
            @Override
            public void callback(final User currentUser) {
                if(!currentUser.getId().equals(receiverID) && !currentUser.getFriendsList().contains(receiverID)){
                    hasUser(receiverID, new BooleanCallback() {
                        @Override
                        public void callback(boolean data) {
                            readUser(receiverID, new UserCallback<User>() {
                                @Override
                                public void callback(User user) {
                                    if(!user.hasFriendRequest(currentUser.getId())){
                                        user.addFriendRequest(currentUser.getId());
                                        updateUser(user);
                                    }

                                }
                            });
                        }
                    });
                }

            }
        });
    }

    public static void sendPartyRequest(final String receiverID){
        callCurrentUser(new CurrentUserCallback() {
            @Override
            public void callback(final User currentUser) {
                if(!currentUser.equals(receiverID) && currentUser.getFriendsList().contains(receiverID)){

                    checkInParty(receiverID, new BooleanCallback() {
                        @Override
                        public void callback(boolean data) {
                            if(!data){
                                readUser(receiverID, new UserCallback<User>() {
                                    @Override
                                    public void callback(User user) {
                                        if(!user.hasPartyRequest(currentUser.getId())) {
                                            user.addPartyRequest(currentUser.getId());
                                            updateUser(user);
                                        }
                                    }
                                });

                            }

                        }
                    });



                }
            }
        });
    }

    public static void acceptFriendRequest(final String senderID){
        callCurrentUser(new CurrentUserCallback() {
            @Override
            public void callback(User currentUser) {
                if(!currentUser.getFriendsList().contains(senderID)) {
                    addFriendBothWays(currentUser, senderID);
                    currentUser.removeFriendRequest(senderID);
                    updateUser(currentUser);
                }
            }
        });
    }

    public static void declineFriendRequest(final String senderID){
        callCurrentUser(new CurrentUserCallback() {
            @Override
            public void callback(User currentUser) {
                currentUser.removeFriendRequest(senderID);
                updateUser(currentUser);
            }
        });
    }


    public static void acceptPartyRequest(final String senderID){
        readUser(senderID, new UserCallback<User>() {
            @Override
            public void callback(final User user) {
                callCurrentUser(new CurrentUserCallback() {
                    @Override
                    public void callback(User currentUser) {
                        addToParty(user, currentUser.getId());
                        currentUser.removePartyRequest(senderID);
                        updateUser(currentUser);
                    }
                });
            }
        });
    }

    public static void declinePartyRequest(final String senderID){
        readUser(senderID, new UserCallback<User>() {
            @Override
            public void callback(final User user) {
                callCurrentUser(new CurrentUserCallback() {
                    @Override
                    public void callback(User currentUser) {
                        currentUser.removePartyRequest(senderID);
                        updateUser(currentUser);
                    }
                });
            }
        });
    }


    public static void autoUpdateCurrentUser(@NonNull final CurrentUserCallback finishedCallback){
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if (firebaseUser != null) {
                    final String currentUserfID = firebaseUser.getUid();
                    userRef.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {


                                User user = ds.getValue(User.class);

                                if(currentUserfID.equals(user.getFirebaseId())){
                                    finishedCallback.callback(user);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    //4  Toast.makeText(Profile.this, "USER ID\n"+currentUser,Toast.LENGTH_SHORT).show();

                } else {
                    //  Toast.makeText(Profile.this, "USER ID\n"+ currentUser,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void isHandleDuplicate(final String handle, final BooleanCallback booleanCallback){
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean flag = false;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                    User user = ds.getValue(User.class);
                    if (user.getId().equals(handle)) {
                        flag = true;
                        break;
                    }
                }
                booleanCallback.callback(flag);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public static void isEmailDuplicate(final String email, final BooleanCallback booleanCallback){
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean flag = false;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                    User user = ds.getValue(User.class);
                    if (user.getEmail().equals(email)) {
                        flag = true;
                        break;
                    }
                }
                booleanCallback.callback(flag);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void createNewParty(){
        callCurrentUser(new CurrentUserCallback() {
            @Override
            public void callback(final User currentUser) {
                checkInParty(currentUser.getId() , new BooleanCallback() {
                    @Override
                    public void callback(boolean data) {
                        Log.d("is User in party?", data + "");
                        if(!data){
                            Party party = new Party();
                            if(party.getparty().get(0).equals(""))
                                party.getparty().clear();
                            party.addToParty(currentUser.getId());
                            DatabaseReference newRef = partyRef.push();
                            party.setId(newRef.getKey());
                            newRef.setValue(party);
                        }
                    }
                });
            }
        });
    }

    public static void kickFromParty(final String usertoBeRemovedID){

        readUser(usertoBeRemovedID, new UserCallback<User>() {
            @Override
            public void callback(User user) {
                getUserParty(user, new PartyCallback<Party>() {
                    @Override
                    public void callback(Party party) {
                        party.leaveParty(usertoBeRemovedID);
                        updateParty(party);
                    }
                });
            }
        });
    }

    public static void leaveFromParty(){

        callCurrentUser(new CurrentUserCallback() {
            @Override
            public void callback(final User currentUser) {

                getUserParty(currentUser, new PartyCallback<Party>() {
                    @Override
                    public void callback(Party party) {

                        if(party.checkLastMember()) {
                            partyRef.child(party.getId()).removeValue();
                        }
                        else{
                            party.leaveParty(currentUser.getId());
                            updateParty(party);
                        }

                    }
                });

            }
        });
    }

    public static void joinPartyByID(final String partyID){
        callCurrentUser(new CurrentUserCallback() {
            @Override
            public void callback(final User currentUser) {

                checkInParty(currentUser.getId(), new BooleanCallback() {
                    @Override
                    public void callback(boolean data) {
                        if(!data){
                            partyRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    outerloop:
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                        GenericTypeIndicator<Party> t = new GenericTypeIndicator<Party>() {
                                        };
                                        Party partyFromDatabase = ds.getValue(t);

                                        if (partyFromDatabase != null) {

                                            if (partyFromDatabase.getId().equals(partyID)) {
                                                partyFromDatabase.addToParty(currentUser.getId());
                                                updateParty(partyFromDatabase);
                                                break outerloop;
                                            }

                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }

                            });
                        }
                    }
                });
            }
        });

    }



}