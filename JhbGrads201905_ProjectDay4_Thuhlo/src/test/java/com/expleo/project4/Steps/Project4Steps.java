package com.expleo.project4.Steps;

import com.expleo.project4.Albums;
import com.expleo.project4.Artist;
import com.expleo.project4.BlogDA;
import com.expleo.project4.DatabaseConnection;
import net.thucydides.core.annotations.Step;
import org.yecht.Data;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

public class Project4Steps
{

    BlogDA blogSteps;

    //Confirm(assert) if database file is found and returns String
    @Step("{0}")
    public String checkDBExist(String dbName)//Check for database
    {
        boolean found= false;
        String recievedMessage;
        try
        {
            found = DatabaseConnection.databaseFound(dbName);
            assertThat(true,equalTo(found));
            recievedMessage = "Database Found " + dbName;
            //initialize db connection
            DatabaseConnection.createConnection(dbName);
        }
        catch (AssertionError e)
        {
           recievedMessage = "Database Not Found " + dbName;
        }
        catch (NullPointerException e)
        {
            recievedMessage = "Database file not found";
        }
       /* catch (SQLException e)
        {
            recievedMessage = e.getMessage();
        }*/
        return recievedMessage;
    }

    //Confirm(assert) if json database file is found and returns String
    @Step("{0}")
    public String checkJSONDBexist(String JSONdbName)//Check for Blog database
    {
        boolean found = false;
        String recievedMessage;

        try
        {
            found = DatabaseConnection.jsonDatabaseFound(JSONdbName);
            assertThat(true,equalTo(found));
            recievedMessage = "JSON Database found :: " + JSONdbName;
        }
        catch (AssertionError e)
        {
            recievedMessage = "JSON Database Not Found :: " + JSONdbName;
        }
        catch (NullPointerException e)
        {
            recievedMessage = "Database file not found";
        }
        return recievedMessage;
    }

    //Used for reporting
    @Step("{0}")
    public void reportMessage(String message)
    {

    }

    //Return Artist object from databse
    @Step
    public Artist getArtistDataFromDB(String artistName)
    {
        Artist objArtist = null;

        try
        {
            objArtist = DatabaseConnection.checkIfArtistExist(artistName);
        }
        catch (NullPointerException e)
        {
            e.getMessage();
        }

        return objArtist;
    }

    //Return all Artist albums from database as array-list
    @Step("Artist ID {0}")
    public ArrayList<Albums> getAlbums(int artistId)
    {
        //Expect to give null pointer if no data is found in data
        return DatabaseConnection.checkIfArtistHaveAlbum(artistId);
    }

    //User for reporting takes array-list of albums objects
    @Step
    public void reportMessage2(ArrayList<Albums> albums)
    {
        for(int i = 0; i < albums.size(); i++)
        {
           reportMessage(albums.get(i).getTitle()+ "\n");
        }
    }

    //Confirm if albums table is readable
    @Step
    public String canReadAlbumsTable()//Check for Albums Table
    {
        boolean found = false;
        String recievedMessage;

        try
        {
            found = DatabaseConnection.canReadAlbumTable();
            assertThat(true,equalTo(found));
            recievedMessage = "Table Albums is Readable";
        }
        catch (AssertionError e)
        {
            recievedMessage = "Cannot Read Albums table \n" +
                    e.getMessage();
        }
        catch (NullPointerException e)
        {
            recievedMessage = "Database file not found, cannot read albums table";
        }
        return recievedMessage;
    }

    //Confirm if artist table is readable
    @Step
    public String canReadArtistTable()//Check for Artist Table
    {
        boolean found = false;
        String recievedMessage;

        try
        {
            found = DatabaseConnection.canReadArtistTable();
            assertThat(true,equalTo(found));
            recievedMessage = "Table Artists is Readable";
        }
        catch (AssertionError e)
        {
            recievedMessage = "Cannot Read Artists table \n" +
                    e.getMessage();
        }
        catch (NullPointerException e)
        {
            recievedMessage = "Database file not found, cannot read artists table";
        }
        return recievedMessage;
    }

    //Confirm if connection to database is closed
    @Step
    public String verifyConnectionsClosed()
    {
        String recievedMessage;
        boolean dbConnectionClosed = false;


        try
        {
            dbConnectionClosed = DatabaseConnection.closeDatabaseConnection();
            assertThat(true,equalTo(dbConnectionClosed));
            recievedMessage = "Database Connection Successfully closed";
        }
        catch (AssertionError e)
        {
            recievedMessage = "Database Connection not Successfully closed!";
        }
        return recievedMessage;
    }

    //Used to delete album from database using artistid
    @Step
    public void deleteAlbumFromDB(int artistID)
    {
        DatabaseConnection.deleteAlbumFromDB(artistID);
    }

    //Used to delete artist from database using artistid
    @Step
    public void deleteArtistFromDB(int artistID)//Delete from Artist table
    {
        DatabaseConnection.deleteArtistFromDB(artistID);
    }

    //Confirm that the album has been deleted using artistid
    @Step
    public String verifyAlbumDelete(int artistId)//Hamcrest for delete from Albums table
    {
        Albums objAlbums = null;
        String recievedMessage;
        try
        {
            objAlbums = DatabaseConnection.verifyDeleteAlbums(artistId);
            assertThat(null,is(equalTo(objAlbums)));
            recievedMessage = "Album  ID : " + artistId + " Deleted Succesfully";
        }
        catch (AssertionError e)
        {
            recievedMessage = "Album ID : " + artistId + " Not Deleted";
        }
        catch (NullPointerException e)
        {
            recievedMessage = "Album ID : " + artistId + " Not Deleted";
        }

        return  recievedMessage;
    }

    //Confirm that artist is deleted from database using artistId
    @Step
    public String verifyArtistDelete(int artistId)//Hamcrest for delete from Artists table
    {
        Artist objArtist = null;
        String recievedMessage;
        try
        {
            objArtist = DatabaseConnection.verifyDeleteArtist(artistId);
            assertThat(null,is(equalTo(objArtist)));
            recievedMessage = "Artist ID : " + artistId + " Deleted Succesfully";
        }
        catch (AssertionError e)
        {
            recievedMessage = "Artist ID : " + artistId + " Not Deleted";
        }
        catch (NullPointerException e)
        {
            recievedMessage = "Artist ID : " + artistId + " Not Deleted";
        }
        return  recievedMessage;
    }





    //////////////////////////////////////////////////////////////////////////////
    //////////////////////////REST Services //////////////////////////////////////


    //Create the connection to REST service
    @Step("REST url {0}")
    public void setUpRESTservice(String url)
    {
        blogSteps.setupRESTFullServices(url);

    }

    //Submit list of Albums as comments to the json server
    @Step
    public void submitNewComment(ArrayList<Albums> arAlbums, int expectedhttpReturnCode)
    {
        String artistName;
        for(int i =0; i < arAlbums.size(); i++)
        {
            artistName = DatabaseConnection.getArtistName(arAlbums.get(i).getArtistsId());
            blogSteps.submitNewComment(artistName + " - " +arAlbums.get(i).getTitle());
            reportMessage(verifyReturnCode(expectedhttpReturnCode));
        }
    }

    //Verify if returned http code is as expected
    @Step
    public String verifyReturnCode(int expectedCode)//Hamcrest for write to Json
    {
        String recievedMessage;
        int recievedCode = 0;

        try
        {
            recievedCode = blogSteps.getReturnCode(expectedCode);
            assertThat(expectedCode,equalTo(recievedCode));
            recievedMessage = "http Response code correct " + recievedCode;
        }
        catch (AssertionError e)
        {
            recievedMessage = "http Response incorrect Expected :" + expectedCode + " Found :" + recievedCode;
        }
        catch (Exception e)
        {
            recievedMessage = e.getMessage();
        }
        return recievedMessage;
    }


}
