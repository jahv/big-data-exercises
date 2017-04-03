package nearsoft.academy.bigdata.recommendation;

import java.util.HashMap;

/**
 * Created by AMDA on 30/03/2017.
 */
class ManageList
{
    public HashMap<String, Long> productList, userList;
    public HashMap<Long, String> invertProductList, invertUserList;

    public long productIndex, userIndex;
    public long totalIndexes;

    public ManageList(){
        productList = new HashMap<String, Long>();
        userList = new HashMap<String, Long>();

        invertProductList = new HashMap<Long, String>();
        invertUserList = new HashMap<Long, String>();

        productIndex = userIndex = 0;
    }

    public long addProduct( String idProd )
    {
        if( !productList.containsKey( idProd ) ){
            productIndex = productList.size() + 1;
            productList.put(idProd, productIndex);
            invertProductList.put(productIndex, idProd);
        }
        else
            productIndex = productList.get( idProd );

        return productIndex;
    }

    public long addUser( String idUser ){
        if( !userList.containsKey(idUser) ){
            userIndex = userList.size() + 1;
            userList.put(idUser, userIndex);
            invertUserList.put(userIndex, idUser);
        }
        else
            userIndex = userList.get( idUser );

        return userIndex;
    }
}
