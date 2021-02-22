package com.oceanaafoods.interfaces;

import static com.oceanaafoods.interfaces.StringConstants.SCRATCHCARDID;
import static com.oceanaafoods.interfaces.StringConstants.USERID;

public interface APIConstants {
    String BASE_URL = "https://cube-media.in/oceano/";
    String MENUTYPE = "common/get_menutypes";
    String MENUITEMS = "common/getmenuitemsbyid/" + StringConstants.MENUID;
    String MENUPRODUCTS = "common/getproductscat/" + StringConstants.MENUID + "/mainmenu";
    String OFFERS = "common/getsliders";
    String REGISTRATION = "Userdetails/registartion";
    String LOGIN = "Userdetails/user_login";
    String USERDETAILS = "Userdetails/get_userdetails_by_id/" + USERID;
    String SCRATCHCARDS = "Userdetails/getscratchcards/" + USERID;
    String UPDATESCRATCHSTATUS = "Userdetails/scratchstatus/" + SCRATCHCARDID;


}
