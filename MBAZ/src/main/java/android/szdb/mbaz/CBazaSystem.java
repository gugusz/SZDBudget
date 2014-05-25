package android.szdb.mbaz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

//W tej klasie znajdują się wszystkie operacje jakie będziemy robić na danych
public class CBazaSystem {
    private CDatabaseManager dbManager;
    private SQLiteDatabase dbOkres;
//    private SQLiteDatabase dbKDO;
//    private SQLiteDatabase dbDochody;
//    private SQLiteDatabase dbKWY;
//    private SQLiteDatabase dbWydatki;
//    private SQLiteDatabase dbSubkategoria;

    private String[] OKRES_REKORD = {CDatabaseManager.OKRES_ID, CDatabaseManager.OKRES_OD, CDatabaseManager.OKRES_DO};
//    private String[] KDO_REKORD = {CDatabaseManager.KDO_ID, CDatabaseManager.KDO_NAZWA};
//    private String[] DOCHODY_REKORD = {CDatabaseManager.KDO_ID, CDatabaseManager.KDO_NAZWA};
//    private String[] KWY_REKORD = {CDatabaseManager.KDO_ID, CDatabaseManager.KDO_NAZWA};
//    private String[] WYDATKI_REKORD = {CDatabaseManager.KDO_ID, CDatabaseManager.KDO_NAZWA};
//    private String[] SUBKATEGORIA_REKORD = {CDatabaseManager.KDO_ID, CDatabaseManager.KDO_NAZWA};

    public CBazaSystem(Context context){
        dbManager = new CDatabaseManager(context);
    }

    public void open(){
        dbOkres = dbManager.getWritableDatabase();
    }

    public void close(){
        dbOkres.close();
    }
    //Zapytanie związane z Okresem
    private COkres parseOkres(Cursor k){
        COkres okr = new COkres();
        okr.setOKRk_1_Id(k.getInt(0));
        okr.setOKR_Od(k.getString(1));
        okr.setOKR_Do(k.getString(2));
        return okr;
    }

    public COkres dodajOkres(String od, String doo){
        ContentValues cv = new ContentValues();
        cv.put(CDatabaseManager.OKRES_OD, od);
        cv.put(CDatabaseManager.OKRES_DO, doo);
        long okrID = dbOkres.insert(CDatabaseManager.TABLE_OKRES, null, cv);
        Cursor kursor = dbOkres.query(CDatabaseManager.TABLE_OKRES, OKRES_REKORD, CDatabaseManager.OKRES_ID + " = " + okrID, null, null, null, null);
        kursor.moveToFirst();
        COkres nowyOKR = parseOkres(kursor);
        kursor.close();
        return nowyOKR;
    }

    public List<COkres> zwrocOkresy(){
        List<COkres> okresy = new ArrayList<COkres>();
        Cursor kursor = dbOkres.query(CDatabaseManager.TABLE_OKRES, OKRES_REKORD, null, null, null, null, null);
        kursor.moveToFirst();
        while (!kursor.isAfterLast()) {
            COkres okr = parseOkres(kursor);
            okresy.add(okr);
            kursor.moveToNext();
        }
        kursor.close();
        return okresy;
    }

    public void usunOkres(COkres okr){
        long id = okr.getOKRk_1_Id();
        dbOkres.delete(CDatabaseManager.TABLE_OKRES, CDatabaseManager.OKRES_ID + " = " + id, null);
    }
/*
    //Zapytania związane z kategorią Dochodów
    private CKat_doch parseKDO(Cursor k){
        CKat_doch kdo = new CKat_doch();
        kdo.setKDOk_1_Id(k.getInt(0));
        kdo.setKDO_Nazwa(k.getString(1));
        return kdo;
    }

    public CKat_doch dodajKDO(String nazwa){
        ContentValues cv = new ContentValues();
        cv.put(CDatabaseManager.KDO_NAZWA, nazwa);
        long kdoID = dbKDO.insert(CDatabaseManager.TABLE_KDO, null, cv);
        Cursor kursor = dbKDO.query(CDatabaseManager.TABLE_KDO, KDO_REKORD, CDatabaseManager.KDO_ID + " = " + kdoID, null, null, null, null);
        kursor.moveToFirst();
        CKat_doch nowyKDO = parseKDO(kursor);
        kursor.close();
        return nowyKDO;
    }

    public List<CKat_doch> zwrocKDO(){
        List<CKat_doch> kategorie = new ArrayList<CKat_doch>();
        Cursor kursor = dbKDO.query(CDatabaseManager.TABLE_KDO, KDO_REKORD, null, null, null, null, null);
        kursor.moveToFirst();
        while (!kursor.isAfterLast()) {
            CKat_doch kdo = parseKDO(kursor);
            kategorie.add(kdo);
            kursor.moveToNext();
        }
        kursor.close();
        return kategorie;
    }

    public void usunKDO(CKat_doch kdo){
        long id = kdo.getKDOk_1_Id();
        dbKDO.delete(CDatabaseManager.TABLE_KDO, CDatabaseManager.KDO_ID + " = " + id, null);
    }

    //Zapytania związane z Dochodami
    private CDochody parseDochody(Cursor k){
        CDochody doc = new CDochody();
        doc.setDOCk_1_Id(k.getInt(0));
        doc.setDOC_Kwota(k.getFloat(1));
        doc.setDOC_Data(k.getString(2));
        doc.setKDO_Id(k.getInt(3));
        doc.setOKR_Id(k.getInt(4));
        return doc;
    }

    public CDochody dodajDochody(float kwota, String data, int kdoid, int okrid){
        ContentValues cv = new ContentValues();
        cv.put(CDatabaseManager.DOCHODY_KWOTA, kwota);
        cv.put(CDatabaseManager.DOCHODY_DATA, data);
        cv.put(CDatabaseManager.KDO_ID_FK_DOCHODY, kdoid);
        cv.put(CDatabaseManager.OKRES_ID_FK_DOCHODY, okrid);
        long dochodyID = dbDochody.insert(CDatabaseManager.TABLE_DOCHODY, null, cv);
        Cursor kursor = dbDochody.query(CDatabaseManager.TABLE_DOCHODY, DOCHODY_REKORD, CDatabaseManager.DOCHODY_ID + " = " + dochodyID, null, null, null, null);
        kursor.moveToFirst();
        CDochody nowyDochod = parseDochody(kursor);
        kursor.close();
        return nowyDochod;
    }

    public List<CDochody> zwrocDochody(){
        List<CDochody> kategorie = new ArrayList<CDochody>();
        Cursor kursor = dbDochody.query(CDatabaseManager.TABLE_DOCHODY, DOCHODY_REKORD, null, null, null, null, null);
        kursor.moveToFirst();
        while (!kursor.isAfterLast()) {
            CDochody doc = parseDochody(kursor);
            kategorie.add(doc);
            kursor.moveToNext();
        }
        kursor.close();
        return kategorie;
    }

    public void usunDochody(CDochody doc){
        long id = doc.getDOCk_1_Id();
        dbDochody.delete(CDatabaseManager.TABLE_DOCHODY, CDatabaseManager.DOCHODY_ID + " = " + id, null);
    }

    //Zapytania związane z Kategoriami Wydatków
    private CKat_wyd parseKWY(Cursor k){
        CKat_wyd kwy = new CKat_wyd();
        kwy.setKWYk_1_Id(k.getInt(0));
        kwy.setKWY_Nazwa(k.getString(1));
        return kwy;
    }

    public CKat_wyd dodajKWY(String nazwa){
        ContentValues cv = new ContentValues();
        cv.put(CDatabaseManager.KWY_NAZWA, nazwa);
        long kwyID = dbKWY.insert(CDatabaseManager.TABLE_KWY, null, cv);
        Cursor kursor = dbKWY.query(CDatabaseManager.TABLE_KWY, KWY_REKORD, CDatabaseManager.KWY_ID + " = " + kwyID, null, null, null, null);
        kursor.moveToFirst();
        CKat_wyd nowyKWY = parseKWY(kursor);
        kursor.close();
        return nowyKWY;
    }

    public List<CKat_wyd> zwrocKWY(){
        List<CKat_wyd> kategorie = new ArrayList<CKat_wyd>();
        Cursor kursor = dbKWY.query(CDatabaseManager.TABLE_KWY, KWY_REKORD, null, null, null, null, null);
        kursor.moveToFirst();
        while (!kursor.isAfterLast()) {
            CKat_wyd kwy = parseKWY(kursor);
            kategorie.add(kwy);
            kursor.moveToNext();
        }
        kursor.close();
        return kategorie;
    }

    public void usunKWY(CKat_wyd kwy){
        long id = kwy.getKWYk_1_Id();
        dbKWY.delete(CDatabaseManager.TABLE_KWY, CDatabaseManager.KWY_ID + " = " + id, null);
    }
    */
}
