package com.realme_demo.realmeapp.vu.models;

import java.nio.Buffer;

/**
 * Created by idanhahn on 8/24/2016.
 */
public class Capsule1 extends MeshObject{
    private Buffer mVertBuff;
    private Buffer mTexCoordBuff;
    private Buffer mNormBuff;
    private Buffer mIndBuff;

    private int indicesNumber = 0;
    private int verticesNumber = 0;


    public Capsule1()
    {
        setVerts();
        setTexCoords();
        setNorms();
        setIndices();
    }


    private void setVerts()
    {
        double[] TEAPOT_VERTS = {-0.500000, -0.500000, 0.500000,
                 0.500000, -0.500000, 0.500000,
                 -0.500000, 0.500000, 0.500000,
                 0.500000, 0.500000, 0.500000,
                 -0.500000, 0.500000, -0.500000,
                 0.500000, 0.500000, -0.500000,
                 -0.500000, -0.500000, -0.500000,
                 0.500000, -0.500000, -0.500000 };
        mVertBuff = fillBuffer(TEAPOT_VERTS);
        verticesNumber = TEAPOT_VERTS.length / 3;
    }


    private void setTexCoords()
    {
        double[] TEAPOT_TEX_COORDS = { 0.000000, 0.000000,
                1.000000, 0.000000,
                0.000000, 1.000000,
                1.000000, 1.000000};
        mTexCoordBuff = fillBuffer(TEAPOT_TEX_COORDS);

    }


    private void setNorms()
    {
        double[] TEAPOT_NORMS = {0.000000, 0.000000, 1.000000,
                0.000000, 1.000000, 0.000000,
                0.000000, 0.000000, -1.000000,
                0.000000, -1.000000, 0.000000,
                1.000000, 0.000000, 0.000000,
                -1.000000, 0.000000, 0.000000 };
        mNormBuff = fillBuffer(TEAPOT_NORMS);
    }


    private void setIndices()
    {
        short[] TEAPOT_INDICES = {
                1,1,1, 2,2,1, 3,3,1,
                3,3,1, 2,2,1, 4,4,1,
                3,1,2, 4,2,2, 5,3,2,
                5,3,2, 4,2,2, 6,4,2,
                5,4,3, 6,3,3, 7,2,3,
                7,2,3, 6,3,3, 8,1,3,
                7,1,4, 8,2,4, 1,3,4,
                1,3,4, 8,2,4, 2,4,4,
                2,1,5, 8,2,5, 4,3,5,
                4,3,5, 8,2,5, 6,4,5,
                7,1,6, 1,2,6, 5,3,6,
                5,3,6, 1,2,6, 3,4,6 };
        mIndBuff = fillBuffer(TEAPOT_INDICES);
        indicesNumber = TEAPOT_INDICES.length;
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
    public Buffer getBuffer(MeshObject.BUFFER_TYPE bufferType)
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
