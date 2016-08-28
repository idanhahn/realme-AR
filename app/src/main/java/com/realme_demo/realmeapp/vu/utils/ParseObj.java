package com.realme_demo.realmeapp.vu.utils;

import android.content.Context;
import android.support.annotation.RawRes;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by idanhahn on 8/24/2016.
 */
public class ParseObj {

    private final static String TAG = "ParseObj";

    // Tokens for parsing.
    private final static String OBJ_VERTEX_TEXTURE = "vt";
    private final static String OBJ_VERTEX_NORMAL = "vn";
    private final static String OBJ_VERTEX = "v";
    private final static String OBJ_FACE = "f";
    private final static String OBJ_GROUP_NAME = "g";
    private final static String OBJ_OBJECT_NAME = "o";
    private final static String OBJ_SMOOTHING_GROUP = "s";
    private final static String OBJ_POINT = "p";
    private final static String OBJ_LINE = "l";
    private final static String OBJ_MAPLIB = "maplib";
    private final static String OBJ_USEMAP = "usemap";
    private final static String OBJ_MTLLIB = "mtllib";
    private final static String OBJ_USEMTL = "usemtl";
    private final static String MTL_NEWMTL = "newmtl";
    private final static String MTL_KA = "Ka";
    private final static String MTL_KD = "Kd";
    private final static String MTL_KS = "Ks";
    private final static String MTL_TF = "Tf";
    private final static String MTL_ILLUM = "illum";
    private final static String MTL_D = "d";
    private final static String MTL_D_DASHHALO = "-halo";
    private final static String MTL_NS = "Ns";
    private final static String MTL_SHARPNESS = "sharpness";
    private final static String MTL_NI = "Ni";
    private final static String MTL_MAP_KA = "map_Ka";
    private final static String MTL_MAP_KD = "map_Kd";
    private final static String MTL_MAP_KS = "map_Ks";
    private final static String MTL_MAP_NS = "map_Ns";
    private final static String MTL_MAP_D = "map_d";
    private final static String MTL_DISP = "disp";
    private final static String MTL_DECAL = "decal";
    private final static String MTL_BUMP = "bump";
    private final static String MTL_REFL = "refl";
    public final static String MTL_REFL_TYPE_SPHERE = "sphere";
    public final static String MTL_REFL_TYPE_CUBE_TOP = "cube_top";
    public final static String MTL_REFL_TYPE_CUBE_BOTTOM = "cube_bottom";
    public final static String MTL_REFL_TYPE_CUBE_FRONT = "cube_front";
    public final static String MTL_REFL_TYPE_CUBE_BACK = "cube_back";
    public final static String MTL_REFL_TYPE_CUBE_LEFT = "cube_left";
    public final static String MTL_REFL_TYPE_CUBE_RIGHT = "cube_right";


    private List<Float> listVerts = new ArrayList<Float>();
    private List<Float> listTexCoords = new ArrayList<Float>();
    private List<Float> listNorms = new ArrayList<Float>();
    private List<Short> listIndices = new ArrayList<Short>();

    public ParseObj(Context c, String filename) throws IOException {
        parseObjFile(c, filename);
    }

    public List<Float> getVerts(){
        return listVerts;
    }

    public List<Float> getTexCoords(){
        return listTexCoords;
    }

    public List<Float> getNorms(){
        return listNorms;
    }

    public List<Short> getIndices(){
        return listIndices;
    }

    private void parseObjFile(Context c, String objFilename) throws IOException {
        int lineCount = 0;
        int resId = c.getResources().getIdentifier(objFilename, "raw", c.getPackageName());

        InputStream is = c.getResources().openRawResource(resId);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String line = null;
        long tStart = System.currentTimeMillis();
        while (true) {

            line = br.readLine();
            if (null == line) {
                break;
            }

            line = line.trim();
            line = line.replace("  "," ");

            if (line.length() == 0) {
                continue;
            }

            // NOTE: we don't check for the space after the char
            // because sometimes it's not there - most notably in the
            // groupname, we seem to get a lot of times where we have
            // "g\n", i.e. setting the group name to blank (or
            // default?)
            if (line.startsWith("#")) // comment
            {
                continue;
            } else if (line.startsWith(OBJ_VERTEX_TEXTURE)) {
                processVertexTexture(line);
            } else if (line.startsWith(OBJ_VERTEX_NORMAL)) {
                processVertexNormal(line);
            } else if (line.startsWith(OBJ_VERTEX)) {
                processVertex(line);
            } else if (line.startsWith(OBJ_FACE)) {
                processFace(line);
            } else {
                Log.d(TAG, "line " + lineCount + " unknown line |" + line + "|");
            }
            lineCount++;
        }
        br.close();
        long tEnd = System.currentTimeMillis();
        double tProcess = (tEnd - tStart) / 1000.0;
        Log.d(TAG, "Loaded " + lineCount + " lines during " + tProcess + " seconds");

    }

    // @TODO: processVertex calls parseFloatList with params expecting
    // only three floats on the line.  If there are more than three
    // floats in line then any extra values will be ignored by
    // parseFloatList.  For the 'v' (geometric vertex) lines, there
    // may be three values (x,y,z) or there may be four (x,y,z and w
    // i.e. weight).  Currently we're ignoring w if it is present.
    //
    // ------------------------------------------------------
    // From the wavefront OBJ file spec;
    // ------------------------------------------------------
    //
    // > v x y z w
    // >
    // >     Polygonal and free-form geometry statement.
    // >
    // >     Specifies a geometric vertex and its x y z coordinates. Rational
    // >     curves and surfaces require a fourth homogeneous coordinate, also
    // >     called the weight.
    // >
    // >     x y z are the x, y, and z coordinates for the vertex. These are
    // >     floating point numbers that define the position of the vertex in
    // >     three dimensions.
    // >
    // >     w is the weight required for rational curves and surfaces. It is
    // >     not required for non-rational curves and surfaces. If you do not
    // >     specify a value for w, the default is 1.0.
    // >
    // >     NOTE: A positive weight value is recommended. Using zero or
    // >     negative values may result in an undefined point in a curve or
    // >     surface.
    private void processVertex(String line) {

        String[] values = line.split(" ");
        for (int i = 1; i < 4; i++) {
            listVerts.add(Float.valueOf(values[i]));
        }

    }

    // ------------------------------------------------------
    // From the wavefront OBJ file spec;
    // ------------------------------------------------------
    //
    //    vt u v w
    //
    //    Vertex statement for both polygonal and free-form geometry.
    //
    //    Specifies a texture vertex and its coordinates. A 1D texture
    //    requires only u texture coordinates, a 2D texture requires both u
    //    and v texture coordinates, and a 3D texture requires all three
    //    coordinates.
    //
    //    u is the value for the horizontal direction of the texture.
    //
    //    v is an optional argument.
    //
    //    v is the value for the vertical direction of the texture. The
    //    default is 0.
    //
    //    w is an optional argument.
    //
    //    w is a value for the depth of the texture. The default is 0.
    private void processVertexTexture(String line) {

        String[] values = line.split(" ");
        for (int i = 1; i < 3; i++) {
            listTexCoords.add(Float.valueOf(values[i]));
        }

    }

    // ------------------------------------------------------
    // From the wavefront OBJ file spec;
    // ------------------------------------------------------
    //
    //     vt u v w
    //
    //     Vertex statement for both polygonal and free-form geometry.
    //
    //     Specifies a texture vertex and its coordinates. A 1D texture
    //     requires only u texture coordinates, a 2D texture requires both u
    //     and v texture coordinates, and a 3D texture requires all three
    //     coordinates.
    //
    //     u is the value for the horizontal direction of the texture.
    //
    //     v is an optional argument.
    //
    //     v is the value for the vertical direction of the texture. The
    //     default is 0.
    //
    //     w is an optional argument.
    //
    //     w is a value for the depth of the texture. The default is 0.
    private void processVertexNormal(String line) {
        String[] values = line.split(" ");
        for (int i = 1; i < 4; i++) {
            listNorms.add(Float.valueOf(values[i]));
        }
    }

    // ------------------------------------------------------
    // From the wavefront OBJ file spec;
    // ------------------------------------------------------
    //
    // Referencing groups of vertices
    //
    // Some elements, such as faces and surfaces, may have a triplet of
    // numbers that reference vertex data.These numbers are the reference
    // numbers for a geometric vertex, a texture vertex, and a vertex normal.
    //
    // Each triplet of numbers specifies a geometric vertex, texture vertex,
    // and vertex normal. The reference numbers must be in order and must
    // separated by slashes (/).
    //
    // o       The first reference number is the geometric vertex.
    //
    // o       The second reference number is the texture vertex. It follows
    // 	the first slash.
    //
    // o       The third reference number is the vertex normal. It follows the
    // 	second slash.
    //
    // There is no space between numbers and the slashes. There may be more
    // than one series of geometric vertex/texture vertex/vertex normal
    // numbers on a line.
    //
    // The following is a portion of a sample file for a four-sided face
    // element:
    //
    //     f 1/1/1 2/2/2 3/3/3 4/4/4
    //
    // Using v, vt, and vn to represent geometric vertices, texture vertices,
    // and vertex normals, the statement would read:
    //
    //     f v/vt/vn v/vt/vn v/vt/vn v/vt/vn
    //
    // If there are only vertices and vertex normals for a face element (no
    // texture vertices), you would enter two slashes (//). For example, to
    // specify only the vertex and vertex normal reference numbers, you would
    // enter:
    //
    //     f 1//1 2//2 3//3 4//4
    //
    // When you are using a series of triplets, you must be consistent in the
    // way you reference the vertex data. For example, it is illegal to give
    // vertex normals for some vertices, but not all.
    //
    // The following is an example of an illegal statement.
    //
    //     f 1/1/1 2/2/2 3//3 4//4
    //
    //  ...
    //
    //     f  v1/vt1/vn1   v2/vt2/vn2   v3/vt3/vn3 . . .
    //
    //     Polygonal geometry statement.
    //
    //     Specifies a face element and its vertex reference number. You can
    //     optionally include the texture vertex and vertex normal reference
    //     numbers.
    //
    //     The reference numbers for the vertices, texture vertices, and
    //     vertex normals must be separated by slashes (/). There is no space
    //     between the number and the slash.
    //
    //     v is the reference number for a vertex in the face element. A
    //     minimum of three vertices are required.
    //
    //     vt is an optional argument.
    //
    //     vt is the reference number for a texture vertex in the face
    //     element. It always follows the first slash.
    //
    //     vn is an optional argument.
    //
    //     vn is the reference number for a vertex normal in the face element.
    //     It must always follow the second slash.
    //
    //     Face elements use surface normals to indicate their orientation. If
    //     vertices are ordered counterclockwise around the face, both the
    //     face and the normal will point toward the viewer. If the vertex
    //     ordering is clockwise, both will point away from the viewer. If
    //     vertex normals are assigned, they should point in the general
    //     direction of the surface normal, otherwise unpredictable results
    //     may occur.
    //
    //     If a face has a texture map assigned to it and no texture vertices
    //     are assigned in the f statement, the texture map is ignored when
    //     the element is rendered.
    //
    //     NOTE: Any references to fo (face outline) are no longer valid as of
    //     version 2.11. You can use f (face) to get the same results.
    //     References to fo in existing .obj files will still be read,
    //     however, they will be written out as f when the file is saved.
    private void processFace(String line) {
        String[] valuesArr = line.split(" ");

        if (valuesArr.length > 4){
            for (int i = 1; i < 4; i++) {
                String[] values = valuesArr[i].split("/");
                //for (int j = 0; j < 3; j++){
                //listIndices.add(Short.valueOf(values[j]));
                listIndices.add((short) (Short.valueOf(values[0]) - 1));
                //}
            }
            for (int i = 1; i < 5; i++) {
                String[] values = valuesArr[i].split("/");
                //for (int j = 0; j < 3; j++){
                //listIndices.add(Short.valueOf(values[j]));
                if (i != 2)
                    listIndices.add((short) (Short.valueOf(values[0]) - 1));
                //}
            }

        } else {
        for (int i = 1; i < 4; i++) {
            String[] values = valuesArr[i].split("/");
            //for (int j = 0; j < 3; j++){
            //listIndices.add(Short.valueOf(values[j]));
            listIndices.add((short) (Short.valueOf(values[0]) - 1));

            //}
        }
        }
    }

}
