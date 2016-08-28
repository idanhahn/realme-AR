package com.realme_demo.realmeapp.vu.models;

import android.content.Context;

import com.realme_demo.realmeapp.vu.utils.ParseObj;

import java.io.IOException;
import java.nio.Buffer;

/**
 * Created by idanhahn on 8/25/2016.
 */
public class Obj3D extends MeshObject{

    private Buffer mVertBuff;
    private Buffer mTexCoordBuff;
    private Buffer mNormBuff;
    private Buffer mIndBuff;

    private int indicesNumber = 0;
    private int verticesNumber = 0;

    private float scaleFactor;


    public Obj3D(Context context, String model, float scaleFactor)
    {
        this.scaleFactor = scaleFactor;
        parseInBackground(context, model);
    }

    public int getNumObjectIndex()
    {
        return indicesNumber;
    }


    @Override
    public int getNumObjectVertex()
    {
        return verticesNumber;
    }


    @Override
    public Buffer getBuffer(BUFFER_TYPE bufferType)
    {
        Buffer result = null;
        switch (bufferType)
        {
            case BUFFER_TYPE_VERTEX:
                result = mVertBuff;
                break;
            case BUFFER_TYPE_TEXTURE_COORD:
                result = mTexCoordBuff;
                break;
            case BUFFER_TYPE_NORMALS:
                result = mNormBuff;
                break;
            case BUFFER_TYPE_INDICES:
                result = mIndBuff;
            default:
                break;

        }

        return result;
    }


    public float getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }


    public void parseInBackground(Context c, String model){

        Runnable pT = new ParseThread(c,model);
        new Thread(pT).start();
    }




    private class ParseThread implements Runnable {

        Context c;
        String model;

        public ParseThread(Context c, String model) {
            this.c = c;
            this.model = model;

        }

        public void run() {
            ParseObj po = null;
            try {
                po = new ParseObj(c, model);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mVertBuff = fillBuffer(po.getVerts());
            verticesNumber = po.getVerts().size() / 3;
            mTexCoordBuff = fillBuffer(po.getTexCoords());
            mNormBuff = fillBuffer(po.getNorms());
            mIndBuff = fillBuffer(po.getIndices(), true);
            indicesNumber = po.getIndices().size();

        }
    }



}
