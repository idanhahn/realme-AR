package com.realme_demo.realmeapp.vu.models;

import android.content.Context;

import com.realme_demo.realmeapp.vu.utils.ParseObj;

import java.io.IOException;
import java.nio.Buffer;

/**
 * Created by idanhahn on 8/24/2016.
 */
public class Capsule extends MeshObject{

    private Buffer mVertBuff;
    private Buffer mTexCoordBuff;
    private Buffer mNormBuff;
    private Buffer mIndBuff;

    private int indicesNumber = 0;
    private int verticesNumber = 0;


    public Capsule(Context context)
    {


        try {
            ParseObj po = new ParseObj(context, "capsule");

            mVertBuff = fillBuffer(po.getVerts());
            verticesNumber = po.getVerts().size() / 3;
            mTexCoordBuff = fillBuffer(po.getTexCoords());
            mNormBuff = fillBuffer(po.getNorms());
            mIndBuff = fillBuffer(po.getIndices(), true);
            verticesNumber = po.getIndices().size();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
