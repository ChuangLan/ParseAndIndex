import java.io.File;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class Test {
	private static String bucketName     = "cis550upload";
	private static String keyName        = "";
	private static String uploadFileName = "carnegie1.json";
	private static String userName = "a";
	private static boolean isPublic = true;


	public static void main(String[] args){
		
		//Upload to s3
	        
		BasicAWSCredentials credentials = new BasicAWSCredentials(
				"AKIAJGGA5UITXGXCRAWA",
				"DTynAl77mTw5nztOzbbIr9vHgM9AHj/KEwxyWpQn");
		AmazonS3 s3client = new AmazonS3Client(credentials);
		File file = new File(uploadFileName);
        try {
        	String name = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1, uploadFileName.length());
        	name = name.substring(name.lastIndexOf("/")+1, name.length());
        	keyName = userName + "/" + name;
        	System.out.println(keyName);
            System.out.println("Uploading a new object to S3 from a file\n");
            s3client.putObject(new PutObjectRequest(
            		                 bucketName, keyName, file));
            
         } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
            		"means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }

        /**Return result**/
        //response.sendRedirect("");
        /**Invoke the Indexer.Index()**/
		Indexer.userName = userName;
		Indexer.Index(uploadFileName);
//		
//		Query query = new Query();
//		query.doQuery("evo", "");
        //new XMLtoJsonConverter().getXMLfromJson("ca.xml");
	}
}
