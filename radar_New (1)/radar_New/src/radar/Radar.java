/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package radar;

import connection.DBFacade;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author ASN
 */
public class Radar {
 
    public static void main(String[] args) {        
        try{
            loadHarbour harbour = new loadHarbour();
            harbour.loadHarbour(0);
        }catch(Exception ex){
            Logger.getLogger(Radar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
