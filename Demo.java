package salesforce;

import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.enterprise.DeleteResult;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.Error;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.Case;
import com.sforce.soap.enterprise.sobject.CaseComment;
import com.sforce.soap.enterprise.sobject.Chatter__c;
import com.sforce.soap.enterprise.sobject.Contact;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class Demo {
  
static final String USERNAME = "guptachetna992@gmail.com";
static final String PASSWORD = "chetna@1592kRV44Y3GoLX83cgxLl8U1cKZ";
  static EnterpriseConnection connection;

  public static void main(String[] args) {

    ConnectorConfig config = new ConnectorConfig();
    config.setUsername(USERNAME);
    config.setPassword(PASSWORD);
    //config.setTraceMessage(true);
    
    try {
      
      connection = Connector.newConnection(config);
      
      // display some current settings
      System.out.println("Auth EndPoint: "+config.getAuthEndpoint());
      System.out.println("Service EndPoint: "+config.getServiceEndpoint());
      System.out.println("Username: "+config.getUsername());
      System.out.println("SessionId: "+config.getSessionId());
      
      // run the different examples
      queryContacts();
      createAccounts();
      //updateAccounts();
      //deleteAccounts();
      //String res =  insertCase("Spring Boot issue" );
      //insertCaseComment(res, "Spring Boot API issue.");
      //insertCaseComment(res , "Spring loader API issue.");
      //insertCaseComment(res , "Spring Security API issue.");
      insertCustomData();
      retrieveCustomData();
      updateCustomAccounts();
      
    } catch (ConnectionException e1) {
        e1.printStackTrace();
    }  

  }
  
  // queries and displays the 5 newest contacts
  private static void queryContacts() {
    
    System.out.println("Querying for the 5 newest Contacts...");
    
    try {
       
      // query for the 5 newest contacts      
      QueryResult queryResults = connection.query("SELECT Id, FirstName, LastName, Account.Name " +
      		"FROM Contact WHERE AccountId != NULL ORDER BY CreatedDate DESC LIMIT 5");
      if (queryResults.getSize() > 0) {
        for (int i=0;i<queryResults.getRecords().length;i++) {
          // cast the SObject to a strongly-typed Contact
          Contact c = (Contact)queryResults.getRecords()[i];
          System.out.println("Id: " + c.getId() + " - Name: "+c.getFirstName()+" "+
              c.getLastName()+" - Account: "+c.getAccount().getName());
        }
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }    
    
  }
  
  // create 5 test Accounts
  private static void createAccounts() {
    
    System.out.println("Creating 5 new test Accounts...");
    Account[] records = new Account[5];
    
    try {
       
      // create 5 test accounts
      for (int i=0;i<5;i++) {
        Account a = new Account();
        a.setName("Test Account "+i);
        a.setBillingCity("gurgaon");
        //a.getAnnualRevenue(3435);
        records[i] = a;
      }
      
      // create the records in Salesforce.com
      SaveResult[] saveResults = connection.create(records);
      
      // check the returned results for any errors
      for (int i=0; i< saveResults.length; i++) {
        if (saveResults[i].isSuccess()) {
          System.out.println(i+". Successfully created record - Id: " + saveResults[i].getId());
        } else {
          Error[] errors = saveResults[i].getErrors();
          for (int j=0; j< errors.length; j++) {
            System.out.println("ERROR creating record: " + errors[j].getMessage());
          }
        }    
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }    
    
  }
  private static String insertCase(String subject) {
	   System.out.println("Creating 1 new Case Object...");
	    Case[] records = new Case[1];
	    String result  = null;
	    Case c = null;
	    try {
	       
	      // create  case Objects
	      for (int i=0;i<1;i++) {
	    	  c = new Case();
	    	  c.setSubject(subject);
	    	  records[i] = c;
	       
	      }
	      
	      // create the records in Salesforce.com
	      SaveResult[] saveResults = connection.create(records);
	      
	      // check the returned results for any errors
	      for (int i=0; i< saveResults.length; i++) {
	        if (saveResults[i].isSuccess()) {
	        	
	        result  = saveResults[i].getId();
	        		System.out.println(i+". Successfully created record - Id: " + saveResults[i].getId());
	        		
	        } else {
	          Error[] errors = saveResults[i].getErrors();
	          for (int j=0; j< errors.length; j++) {
	            System.out.println("ERROR creating record: " + errors[j].getMessage());
	          }
	        }    
	      }
	      
	    } catch (Exception e) {
	      e.printStackTrace();
	    }    
	    
	    return result;
	  
 }

private static void insertCaseComment(String caseId, String comments) {
		  

		  
		    System.out.println("Creating 1 new Case Comment Object...");
		    CaseComment[] records = new CaseComment[1];
		    
		    try {
		       
		      // create  case comments
		      for (int i=0;i<1;i++) {
		    	  CaseComment comment = new CaseComment();
		    	
		    	  comment.setCommentBody(comments);
		    	  /*	if(c != null)*/
		    	  comment.setParentId(caseId );
		    	
		        records[i] = comment;
		      }
		      
		      // create the records in Salesforce.com
		      SaveResult[] saveResults = connection.create(records);
		      
		      // check the returned results for any errors
		      for (int i=0; i< saveResults.length; i++) {
		        if (saveResults[i].isSuccess()) {
		          System.out.println(i+". Successfully created record - Id: " + saveResults[i].getId());
		        } else {
		          Error[] errors = saveResults[i].getErrors();
		          for (int j=0; j< errors.length; j++) {
		            System.out.println("ERROR creating record: " + errors[j].getMessage());
		          }
		        }    
		      }
		      
		    } catch (Exception e) {
		      e.printStackTrace();
		    }    
		    
		  
	  }
private static String getCaseNumber(String id) {
	  
	  System.out.println("Querying for Book custom Object...");
	    
	  String res = null;
	    try {
	       
	      // query for the 5 newest contacts      
	      QueryResult queryResults = connection.query("SELECT CaseNumber " +
	      		"FROM Case where Id='" + id+"'");
	      if (queryResults.getSize() >= 1) {
	        for (int i=0;i<queryResults.getRecords().length;i++) {
	          // cast the SObject to a strongly-typed Contact
	        	Case c = (Case)queryResults.getRecords()[i];
	        	res = c.getCaseNumber();
	          System.out.println("Id: " + c.getCaseNumber()) ;
	        }
	      }
	      
	    } catch (Exception e) {
	      e.printStackTrace();
	    }  
	    
	    return res;
}
private static void getCommentHistory(String id) {
	  
	  System.out.println("Querying for Case Object...");
	    
	    try {
	       
	      // query for the 5 newest contacts      
	      QueryResult queryResults = connection.query("SELECT Id,CommentBody FROM CaseComment where Parent.CaseNumber ='" + id +"'");
	    //  QueryResult queryResults = connection.query("Select Id,  CommentBody  From CaseComment ");
		   
	    if (queryResults.getSize() >= 1) {
	        for (int i=0;i<queryResults.getRecords().length;i++) {
	          // cast the SObject to a strongly-typed Contact
	        	CaseComment c = (CaseComment)queryResults.getRecords()[i];
	          System.out.println("Id: " + c.getCommentBody()) ;
	        }
	      }
	      
	    } catch (Exception e) {
	      e.printStackTrace();
	    }    
}

private static void retrieveCustomData() {
    
    System.out.println("Querying for the ChatterBox Data...");
    
    try {
       
      // query for the 5 newest contacts      
      QueryResult queryResults = connection.query("SELECT SchoolName__c,Password__c,Status__c FROM Chatter__c");
  /*   DescribeSObjectResult res =  connection.describeSObject("Contact");
    System.out.println(res.getFields()[1]);
  */    if (queryResults.getSize() > 1) {
        for (int i=0;i<1;i++) {
          // cast the SObject to a strongly-typed Contact
        	Chatter__c c = (Chatter__c)queryResults.getRecords()[i];
          System.out.println("School Name: " + c.getSchoolName__c()) ;
          System.out.println("password: " + c.getPassword__c()) ;
          System.out.println("status: " + c.getStatus__c()) ;
        }
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }    
    
  }	

private static void insertCustomData() {
System.out.println("Creating 1 new ChatterBoardData Object...");
    Chatter__c[] records = new Chatter__c[1];
    
    try {
       
      // create 5 test accounts
      for (int i=0;i<1;i++) {
        Chatter__c data= new Chatter__c();
      
       data.setSchoolName__c("qwer");
   data.setPassword__c("chetnagupta");
   data.setStatus__c(true);
  
        //data.setSF_NSF__c(true);
       //data.setF_L_Name__c(true);
        //data.setID_Number_Authentication__c(true);
       //data.setOnly_SSPR__c(true);
        //data.setExternal_Resource__c(true);
        records[i] = data;
      }
      
      // create the records in Salesforce.com
      SaveResult[] saveResults = connection.create(records);
      
      // check the returned results for any errors
      for (int i=0; i< saveResults.length; i++) {
        if (saveResults[i].isSuccess()) {
          System.out.println(i+". Successfully created record - Id: " + saveResults[i].getId());
        } else {
          Error[] errors = saveResults[i].getErrors();
          for (int j=0; j< errors.length; j++) {
            System.out.println("ERROR creating record: " + errors[j].getMessage());
          }
        }    
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }    
    
}

private static void updateCustomAccounts() {
    
    System.out.println("Update ");
    Chatter__c[] records = new Chatter__c[1];
    try {
       
      QueryResult queryResults = connection.query("SELECT Password__c FROM Chatter__c ");
      if (queryResults.getSize() > 0) {
        for (int i=0;i<1;i++) {
          // cast the SObject to a strongly-typed Account
        	Chatter__c  a = (Chatter__c) queryResults.getRecords()[i];
        	
          //System.out.println("Updating Id: " + a.getId() + " - Name: "+a.getName());
          // modify the name of the Account
          //a.setName(a.getName()+" -- UPDATED");
          //a.setBillingCity("kota");
          a.setPassword__c("chet");
          System.out.println(a.getPassword__c());
          records[i] = a;
        }
      }
      
      // update the records in Salesforce.com
      SaveResult[] saveResults = connection.update(records);
      
      // check the returned results for any errors
      for (int i=0; i< saveResults.length; i++) {
        if (saveResults[i].isSuccess()) {
          System.out.println(i+". Successfully updated record - Id: " + saveResults[i].getId());
        } else {
          Error[] errors = saveResults[i].getErrors();
          for (int j=0; j< errors.length; j++) {
            System.out.println("ERROR updating record: " + errors[j].getMessage());
          }
        }    
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }    
    
  }
  // updates the 5 newly created Accounts
  private static void updateAccounts() {
    
    System.out.println("Update the 5 new test Accounts...");
    Account[] records = new Account[5];
    
    try {
       
      QueryResult queryResults = connection.query("SELECT Id, Name FROM Account ORDER BY " +
      		"CreatedDate DESC LIMIT 5");
      if (queryResults.getSize() > 0) {
        for (int i=0;i<queryResults.getRecords().length;i++) {
          // cast the SObject to a strongly-typed Account
          Account a = (Account)queryResults.getRecords()[i];
          System.out.println("Updating Id: " + a.getId() + " - Name: "+a.getName());
          // modify the name of the Account
          a.setName(a.getName()+" -- UPDATED");
          a.setBillingCity("kota");
          records[i] = a;
        }
      }
      
      // update the records in Salesforce.com
      SaveResult[] saveResults = connection.update(records);
      
      // check the returned results for any errors
      for (int i=0; i< saveResults.length; i++) {
        if (saveResults[i].isSuccess()) {
          System.out.println(i+". Successfully updated record - Id: " + saveResults[i].getId());
        } else {
          Error[] errors = saveResults[i].getErrors();
          for (int j=0; j< errors.length; j++) {
            System.out.println("ERROR updating record: " + errors[j].getMessage());
          }
        }    
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }    
    
  }
  
  // delete the 5 newly created Account
  private static void deleteAccounts() {
    
    System.out.println("Deleting the 5 new test Accounts...");
    String[] ids = new String[5];
    
    try {
       
      QueryResult queryResults = connection.query("SELECT Id, Name FROM Account ORDER BY " +
      		"CreatedDate DESC LIMIT 5");
      if (queryResults.getSize() > 0) {
        for (int i=0;i<queryResults.getRecords().length;i++) {
          // cast the SObject to a strongly-typed Account
          Account a = (Account)queryResults.getRecords()[i];
          // add the Account Id to the array to be deleted
          ids[i] = a.getId();
          
          System.out.println("Deleting Id: " + a.getId() + " - Name: "+a.getName());
        }
      }
      
      // delete the records in Salesforce.com by passing an array of Ids
      DeleteResult[] deleteResults = connection.delete(ids);
      
      // check the results for any errors
      for (int i=0; i< deleteResults.length; i++) {
        if (deleteResults[i].isSuccess()) {
          System.out.println(i+". Successfully deleted record - Id: " + deleteResults[i].getId());
        } else {
          Error[] errors = deleteResults[i].getErrors();
          for (int j=0; j< errors.length; j++) {
            System.out.println("ERROR deleting record: " + errors[j].getMessage());
          }
        }    
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }    
    
  }
 
}
