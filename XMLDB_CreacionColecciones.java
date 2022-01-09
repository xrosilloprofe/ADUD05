package ieslavereda.es.ADUD05Ej2;

import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.modules.CollectionManagementService;

public class XMLDB_CreacionColecciones {

    private static Collection obtenColeccion(String nomCol) throws Exception {
        Database dbDriver;
        Collection col;
        dbDriver = (Database) Class.forName("org.exist.xmldb.DatabaseImpl").newInstance();
        DatabaseManager.registerDatabase(dbDriver);
        col = DatabaseManager.getCollection(
                "xmldb:exist://localhost:8081/exist/xmlrpc/db" + nomCol,
                "professor", "1234");
        return col;
    }

    public static void main(String[] args) {
        Collection col = null;
        try {
            col = obtenColeccion("");
            System.out.println("Colección actual: " + col.getName());
            System.out.println(col.getChildCollectionCount()+" colecciones hijas antes.");
            CollectionManagementService cmServ = (CollectionManagementService) col.getService("CollectionManagementService", "1.0");
            cmServ.createCollection("prueba1");
            cmServ.createCollection("pruebas");
            cmServ.removeCollection("prueba1");
            System.out.println(col.getChildCollectionCount()+" colecciones hijas después.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (col != null) {
                    col.close();
                }
            } catch (XMLDBException xe) {
                xe.printStackTrace();
            }
        }
    }
}
