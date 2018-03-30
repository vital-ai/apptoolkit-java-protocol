package com.jibo.apptoolkit.protocol.model;

/*
 * Created by Jibo, Inc. on 11.10.17.
 */
/** 
 * This is the base class for both RequestHeader and ResponseHeader
 */
 abstract public class Header {

     private String SessionID;
     private String TransactionID;

     /** @hide */
     public Header() {
     } 

     /** @hide */
     public Header(String transactionID) {
         TransactionID = transactionID;
     }

     /**
      * Base class constructor. This should never be directly invoked since this 
      * class cannot be directly constructed.
      * @param [transactionID] The Id for the transaction this command is a part of
      * @param [sessionID] The Id for the session this command is a part of
      */
     public Header(String transactionID, String sessionID) {
         TransactionID = transactionID;
         SessionID = sessionID;
     }

     /**
      * Get the id for the session of which this message belongs to
      */
     public String getSessionID() {
         return SessionID;
     }

     /**
      * Get the id for which this message is a part of.  
      * Each Request Message will have a corresponding Response Message 
      * which will share the same transaction id.
      */
     public String getTransactionID() {
         return TransactionID;
     }

     /**
      * The Request header is attached to any Command message being sent to the robot from a client.
      */
     static public class RequestHeader extends Header {
         static public final String VER_1 = "1.0";

         private String AppID = "ImmaLittleTeapot";
         private String Credentials;
         private String Version = VER_1;

         /** 
          * This should only be used for the {@link Command.SessionRequest} command. 
          * Upon receiving a successful response for the SessionRequest command, 
          * it will include a sessionId for us to use with subsequent commands.
          */
         public RequestHeader(String transactionID) {
             super(transactionID);
         }

         /**
          * Construct a RequestHeader for a message which is sent by the client and received by the robot.
          * @param transactionID A unique Id which the client comes up with to distinguish send/
          * receive command transaction from other commands within the session
          * @param sessionID The Id which is included in the response to the SessionRequest Command. 
          * This Id is generated by the robot and is used to distinguish a particular
          * command transaction session from a given client.  The same sessionId should be supplied 
          * if the client is unexectedly disconnected from the robot and you wish to 
          * re-establish the prior session.
          * @param version The version of the protocol this Command will speak. 
          * Refer to `Header.RequestHeader.VER_1` for an example of valid values for this parameter.  
          * Supplying a Command from a different protocol version will result 
          * in a rejected Command from the robot.
          */
         public RequestHeader(String transactionID, String sessionID, String version) {
             super(transactionID, sessionID);
             this.Version = version;
         }

         /** @hide */
         public RequestHeader(String sessionID, String transactionID, String appID, String credentials, String version) {
             super(sessionID, transactionID);
             AppID = appID;
             Credentials = credentials;
             Version = version;
         }

         /** @hide */
         public String getAppID() {
             return AppID;
         }

         /** @hide */
         public String getCredentials() {
             return Credentials;
         }

        /**
          * The verison of the protocol which this message will speak
          */
         public String getVersion() {
             return Version;
         }
     }

     /**
      * The Reponse header is attached to any Command message being received from the robot, 
      * delivered to the client.
      */
     static public class ResponseHeader extends Header {
         private String RobotID;

         /** @hide */
         public ResponseHeader() {
         }

         /** @hide */
         public ResponseHeader(String transactionID) {
             super(transactionID);
         }

         /**
          * Construct a ResponseHeader for a Command which has been sent by the robot and 
          * received by the client.
          * @param transactionID The Id for the transaction this command is a part of
          * @param sessionID The Id for the session this command is a part of
          */
         public ResponseHeader(String transactionID, String sessionID) {
             super(transactionID, sessionID);
         }

        /**
          * The name of the robot from which this message came from.  
          * `Four-Word-Serial-Name` found on robot's base.
          */
         public String getRobotID() {
             return RobotID;
         }

     }

 }
