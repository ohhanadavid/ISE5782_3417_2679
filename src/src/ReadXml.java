package src;


        import javax.xml.parsers.DocumentBuilderFactory;
        import javax.xml.parsers.DocumentBuilder;
        import javax.xml.parsers.ParserConfigurationException;

        import src.geometries.*;
        import org.w3c.dom.Document;
        import org.w3c.dom.NodeList;
        import org.w3c.dom.Node;
        import org.w3c.dom.Element;
        import org.xml.sax.SAXException;
        import src.lighting.AmbientLight;
        import src.primitives.Color;
        import src.primitives.Double3;
        import src.primitives.Point;
        import src.primitives.Vector;
        import src.scene.Scene;
        import java.io.File;
        import java.io.IOException;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

public class ReadXml {



    /**
     * the function get a file name in xml, read it and make a scene from the data
     * @param scene_Name the scene name
     * @param file_name the xml file name
     * @return the scene of the file
     */
    public static Scene ReadFile(String scene_Name, String file_name) {

        Scene scene = new Scene(scene_Name);
        File xmlFile = new File(file_name);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {

            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            Element node = doc.getDocumentElement();

            //get the background color and set in the scene
            Point backColor = parsePoint(node.getAttribute("background-color"));
            Color Cb = new Color(backColor.getX(), backColor.getY(), backColor.getZ());
            scene.setBackground(Cb);

            //get the ambient light color and set in the scene
            Point ambientColor = parsePoint(((Element) doc.getElementsByTagName("ambient-light").item(0)).getAttribute("color"));
            Color Ca = new Color(ambientColor.getX(), ambientColor.getY(), ambientColor.getZ());
            scene.setAmbientLight(new AmbientLight(Ca, new Double3(1,1,1)));

            //new geometries for the scene
            Geometries geoList = new Geometries();

            //get all the triangle
            NodeList nodeList = doc.getElementsByTagName("triangle");
            for (int i = 0; i < nodeList.getLength(); i++) {
                geoList.add(getTriangle(nodeList.item(i)));
            }
            //get all the Polygon
            nodeList = doc.getElementsByTagName("polygon");
            for (int i = 0; i < nodeList.getLength(); i++) {
                geoList.add(getPolygon(nodeList.item(i)));
            }

            //get all the sphere
            nodeList = doc.getElementsByTagName("sphere");
            for (int i = 0; i < nodeList.getLength(); i++) {
                geoList.add(getSphere(nodeList.item(i)));
            }
            //get all the plane
            nodeList = doc.getElementsByTagName("plane");
            for (int i = 0; i < nodeList.getLength(); i++) {
                geoList.add(getPlane(nodeList.item(i)));
            }

            //set the geometries in the scene
            scene.setGeometries(geoList);

        }
        catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }

        return scene;
    }

    /**
     * the function create a triangle from the node
     * @param node
     * @return Triangle
     */
    private static Triangle getTriangle(Node node) {

        Triangle geo = null;
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            Point p0 = parsePoint(((Element) node).getAttribute("p0"));
            Point p1 = parsePoint(((Element) node).getAttribute("p1"));
            Point p2 = parsePoint(((Element) node).getAttribute("p2"));
            geo = new Triangle(p0, p1, p2);
        }
        return geo;
    }


    /**
     * the function create a sphere from the node
     * @param node
     * @return Sphere
     */
    private static Sphere getSphere(Node node) {

        Sphere geo=null;
        if (node.getNodeType() == Node.ELEMENT_NODE) {

            Element element = (Element) node;
            Point p0 = parsePoint(((Element) node).getAttribute("center"));
            int r= Integer.parseInt( ((Element) node).getAttribute("radius"));
            geo=new Sphere(p0,r);
        }
        return geo;
    }

    /**
     * the function create a Plane from the node
     * @param node
     * @return Plane
     */
    private static Plane getPlane(Node node) {

        Plane geo=null;
        if (node.getNodeType() == Node.ELEMENT_NODE) {

            Element element = (Element) node;
            Point p0 = parsePoint(((Element) node).getAttribute("p0"));
            Point v0 = parsePoint(((Element) node).getAttribute("vector"));
            Vector v=new Vector(v0.getX(),v0.getY(),v0.getZ());
            geo=new Plane(p0,v);
        }
        return geo;
    }

    /**
     * the function create a Polygon from the node
     * @param node
     * @return Polygon
     */
    private static Polygon getPolygon(Node node) {

        Polygon geo=null;
        Point[] arr=null;
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            int len=node.getAttributes().getLength();
            arr=new Point[len];
            //  var points=node.getAttributes();
            for (int i=0;i<len;i++)
            {
                Point p0 = parsePoint(((Element) node).getAttribute("p"+i));
                arr[i]=p0;
            }
            geo=new Polygon(arr);
        }
        return geo;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

    /**
     * the function return point from string point
     * @param s string
     * @return Point
     */
    public static Point parsePoint(String s) {

        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(s);
        int[] arr = new int[3];
        int i = 0;

        while (m.find()) {
            arr[i++] = Integer.parseInt(m.group());
        }
        return new Point(arr[0], arr[1], arr[2]);
    }
}


