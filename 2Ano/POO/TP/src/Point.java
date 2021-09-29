import java.awt.geom.Point2D;
/**
 * Escreva a descrição da classe Point aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public abstract class Point extends Point2D {
    
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 1.609344;        
        return (dist);
    }
    
        /* The function to convert decimal into radians */
    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }       
        /* The function to convert radians into decimal */
    public static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
