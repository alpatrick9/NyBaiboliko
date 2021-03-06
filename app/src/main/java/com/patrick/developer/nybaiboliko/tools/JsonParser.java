package com.patrick.developer.nybaiboliko.tools;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by developer on 10/5/16.
 */

public class JsonParser {
    /**
     * Tag pour recuperation copyright version bookIndex
     */
    private static final String TAG_URL = "copyright_url";
    private static final String TAG_MENTION = "copyright_mention";
    private static final String TAG_CODE = "code";
    private static final String TAG_NAME = "name";
    private static final String TAG_ORDER = "order";

    public String getJsonFile(Context appContext, String fileName) throws IOException {
        String jsonFile = null;
        try{
            InputStream is = appContext.getAssets().open(fileName+".json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            jsonFile = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return jsonFile;

    }

    public String getBook(Context appContext, int id){
        String book = "";
        try{
            String jsonFile = getJsonFile(appContext, "boky_baiboly");
            if(jsonFile != null){
                JSONArray books = new JSONArray(jsonFile);
                book = books.getString(id);
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
        return book;
    }

    public Integer getNumberChapOf(Context context,int book){
        String json;
        JSONArray all;
        Integer numberOfChap = 0;
        try{
            json = getJsonFile(context,"toko_baiboly");
            if(json != null){
                all = new JSONArray(json);
                numberOfChap = all.getInt(book);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return numberOfChap;
    }

    public Integer getNumberVersetOf(Context context,int book, int chapitre) {
        String json;
        JSONArray all;
        JSONArray allVerset;
        Integer numberOfVerset = 0;
        try{
            json = getJsonFile(context,"andininy_baiboly");
            if(json != null){
                all = new JSONArray(json);
                allVerset = all.getJSONArray(book);
                numberOfVerset = allVerset.getInt(chapitre);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return numberOfVerset;
    }

    public boolean isJsonFileExistLocal(Context appContext, String fileName) throws IOException{
        boolean value = false;
        if(getJsonFile(appContext, fileName) != null) value = true;
        return value;
    }
}
