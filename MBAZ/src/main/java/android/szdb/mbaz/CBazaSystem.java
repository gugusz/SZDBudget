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
    private SQLiteDatabase dbKDO;
    private SQLiteDatabase dbDochody;
    private SQLiteDatabase dbKWY;
    private SQLiteDatabase dbSubkategoria;
    private SQLiteDatabase dbWydatki;
    private SQLiteDatabase dbPLanowanie;


    private String[] OKRES_REKORD = {CDatabaseManager.OKRES_ID, CDatabaseManager.OKRES_OD, CDatabaseManager.OKRES_DO};
    private String[] KDO_REKORD = {CDatabaseManager.KDO_ID, CDatabaseManager.KDO_NAZWA};
    private String[] DOCHODY_REKORD = {CDatabaseManager.DOCHODY_ID, CDatabaseManager.DOCHODY_KWOTA, CDatabaseManager.DOCHODY_DATA, CDatabaseManager.KDO_ID_FK_DOCHODY};
    private String[] KWY_REKORD = {CDatabaseManager.KWY_ID, CDatabaseManager.KWY_NAZWA};
    private String[] SUBKATEGORIA_REKORD = {CDatabaseManager.SUB_ID, CDatabaseManager.SUB_NAZWA};
    private String[] WYDATKI_REKORD = {CDatabaseManager.WYDATKI_ID, CDatabaseManager.WYDATKI_KWOTA, CDatabaseManager.WYDATKI_DATA, CDatabaseManager.KWY_ID_FK_WYDATKI, CDatabaseManager.SUB_ID_FK_WYDATKI};
    private String[] PLANOWANIE_REKORD = {CDatabaseManager.PLANOWANIE_ID, CDatabaseManager.PLANOWANIE_NAZWA, CDatabaseManager.PLANOWANIE_OD, CDatabaseManager.PLANOWANIE_DATA_ZAK};

    public CBazaSystem(Context context){
        dbManager = new CDatabaseManager(context);
    }

    public void open(){
        dbOkres = dbManager.getWritableDatabase();
        dbKDO = dbManager.getWritableDatabase();
        dbDochody = dbManager.getWritableDatabase();
        dbKWY = dbManager.getWritableDatabase();
        dbSubkategoria = dbManager.getWritableDatabase();
        dbWydatki = dbManager.getWritableDatabase();
        dbPLanowanie = dbManager.getWritableDatabase();
    }

    public void close(){
        dbOkres.close();
        dbKDO.close();
        dbDochody.close();
        dbKWY.close();
        dbSubkategoria.close();
        dbWydatki.close();
        dbPLanowanie.close();
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
        return doc;
    }

    public CDochody dodajDochody(float kwota, String data, int kdoid){
        ContentValues cv = new ContentValues();
        cv.put(CDatabaseManager.DOCHODY_KWOTA, kwota);
        cv.put(CDatabaseManager.DOCHODY_DATA, data);
        cv.put(CDatabaseManager.KDO_ID_FK_DOCHODY, kdoid);
        long dochodyID = dbDochody.insert(CDatabaseManager.TABLE_DOCHODY, null, cv);
        Cursor kursor = dbDochody.query(CDatabaseManager.TABLE_DOCHODY, DOCHODY_REKORD, CDatabaseManager.DOCHODY_ID + " = " + dochodyID, null, null, null, null);
        kursor.moveToFirst();
        CDochody nowyDochod = parseDochody(kursor);
        kursor.close();
        return nowyDochod;
    }

    public List<CDochody> zwrocDochody(){
        List<CDochody> dochody = new ArrayList<CDochody>();
        Cursor kursor = dbDochody.query(CDatabaseManager.TABLE_DOCHODY, DOCHODY_REKORD, null, null, null, null, null);
        kursor.moveToFirst();
        while (!kursor.isAfterLast()) {
            CDochody doc = parseDochody(kursor);
            dochody.add(doc);
            kursor.moveToNext();
        }
        kursor.close();
        return dochody;
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

    //zapytanie związane z Subkategoriami
    private CSubkategoria parseSubkategoria(Cursor k){
        CSubkategoria sub = new CSubkategoria();
        sub.setSUBk_1_Id(k.getInt(0));
        sub.setSUB_Nazwa(k.getString(1));
        return sub;
    }

    public CSubkategoria dodajSubkategorie(String nazwa){
        ContentValues cv = new ContentValues();
        cv.put(CDatabaseManager.SUB_NAZWA, nazwa);
        long subID = dbSubkategoria.insert(CDatabaseManager.TABLE_SUB, null, cv);
        Cursor kursor = dbSubkategoria.query(CDatabaseManager.TABLE_SUB, SUBKATEGORIA_REKORD, CDatabaseManager.SUB_ID + " = " + subID, null, null, null, null);
        kursor.moveToFirst();
        CSubkategoria nowySUB = parseSubkategoria(kursor);
        kursor.close();
        return nowySUB;
    }

    public List<CSubkategoria> zwrocSubkategorie(){
        List<CSubkategoria> subkategorie = new ArrayList<CSubkategoria>();
        Cursor kursor = dbSubkategoria.query(CDatabaseManager.TABLE_SUB, SUBKATEGORIA_REKORD, null, null, null, null, null);
        kursor.moveToFirst();
        while (!kursor.isAfterLast()) {
            CSubkategoria sub = parseSubkategoria(kursor);
            subkategorie.add(sub);
            kursor.moveToNext();
        }
        kursor.close();
        return subkategorie;
    }

    public void usunSubkategorie(CSubkategoria sub) {
        long id = sub.getSUBk_1_Id();
        dbSubkategoria.delete(CDatabaseManager.TABLE_SUB, CDatabaseManager.SUB_ID + " = " + id, null);
    }

    //Zapytania związane z Wydatkami
    private CWydatki parseWydatki(Cursor k){
        CWydatki wyd = new CWydatki();
        wyd.setWYDk_1_Id(k.getInt(0));
        wyd.setWYD_Kwota(k.getFloat(1));
        wyd.setWYD_Data(k.getString(2));
        wyd.setKWY_Id(k.getInt(3));
        wyd.setSUB_Id(k.getInt(4));
        return wyd;
    }

    public CWydatki dodajWydatki(float kwota, String data, int kwyid, int subid){
        ContentValues cv = new ContentValues();
        cv.put(CDatabaseManager.WYDATKI_KWOTA, kwota);
        cv.put(CDatabaseManager.WYDATKI_DATA, data);
        cv.put(CDatabaseManager.KWY_ID_FK_WYDATKI, kwyid);
        cv.put(CDatabaseManager.SUB_ID_FK_WYDATKI, subid);
        long wydatkiID = dbWydatki.insert(CDatabaseManager.TABLE_WYDATKI, null, cv);
        Cursor kursor = dbWydatki.query(CDatabaseManager.TABLE_WYDATKI, WYDATKI_REKORD, CDatabaseManager.WYDATKI_ID + " = " + wydatkiID, null, null, null, null);
        kursor.moveToFirst();
        CWydatki nowyWydatek = parseWydatki(kursor);
        kursor.close();
        return nowyWydatek;
    }

    public CWydatki dodajWydatkiNull(float kwota, String data, int kwyid){
        ContentValues cv = new ContentValues();
        cv.put(CDatabaseManager.WYDATKI_KWOTA, kwota);
        cv.put(CDatabaseManager.WYDATKI_DATA, data);
        cv.put(CDatabaseManager.KWY_ID_FK_WYDATKI, kwyid);
        cv.putNull(CDatabaseManager.SUB_ID_FK_WYDATKI);
        long wydatkiID = dbWydatki.insert(CDatabaseManager.TABLE_WYDATKI, null, cv);
        Cursor kursor = dbWydatki.query(CDatabaseManager.TABLE_WYDATKI, WYDATKI_REKORD, CDatabaseManager.WYDATKI_ID + " = " + wydatkiID, null, null, null, null);
        kursor.moveToFirst();
        CWydatki nowyWydatek = parseWydatki(kursor);
        kursor.close();
        return nowyWydatek;
    }

    public List<CWydatki> zwrocWydatki(){
        List<CWydatki> wydatki = new ArrayList<CWydatki>();
        Cursor kursor = dbWydatki.query(CDatabaseManager.TABLE_WYDATKI, WYDATKI_REKORD, null, null, null, null, null);
        kursor.moveToFirst();
        while (!kursor.isAfterLast()) {
            CWydatki wyd = parseWydatki(kursor);
            wydatki.add(wyd);
            kursor.moveToNext();
        }
        kursor.close();
        return wydatki;
    }

    public void usunWydatki(CWydatki wyd){
        long id = wyd.getWYDk_1_Id();
        dbWydatki.delete(CDatabaseManager.TABLE_WYDATKI, CDatabaseManager.WYDATKI_ID + " = " + id, null);
    }

    //Zapytanie związane z Planowaniem
    private CPlanowanie parsePlanowanie(Cursor k){
        CPlanowanie pla = new CPlanowanie();
        pla.setPLAk_1_Id(k.getInt(0));
        pla.setPLA_NazwaPrzedmiotu(k.getString(1));
        pla.setPLA_Od(k.getString(2));
        pla.setPLA_DataZakupu(k.getString(3));
        return pla;
    }

    public CPlanowanie dodajPlan(String nazwa, String od, String datazak){
        ContentValues cv = new ContentValues();
        cv.put(CDatabaseManager.PLANOWANIE_NAZWA, nazwa);
        cv.put(CDatabaseManager.PLANOWANIE_OD, od);
        cv.put(CDatabaseManager.PLANOWANIE_DATA_ZAK, datazak);
        long plaID = dbPLanowanie.insert(CDatabaseManager.TABLE_PLANOWANIE, null, cv);
        Cursor kursor = dbPLanowanie.query(CDatabaseManager.TABLE_PLANOWANIE, PLANOWANIE_REKORD, CDatabaseManager.PLANOWANIE_ID + " = " + plaID, null, null, null, null);
        kursor.moveToFirst();
        CPlanowanie nowyPLA = parsePlanowanie(kursor);
        kursor.close();
        return nowyPLA;
    }

    public List<CPlanowanie> zwrocPlany(){
        List<CPlanowanie> plany = new ArrayList<CPlanowanie>();
        Cursor kursor = dbPLanowanie.query(CDatabaseManager.TABLE_PLANOWANIE, PLANOWANIE_REKORD, null, null, null, null, null);
        kursor.moveToFirst();
        while (!kursor.isAfterLast()) {
            CPlanowanie pla = parsePlanowanie(kursor);
            plany.add(pla);
            kursor.moveToNext();
        }
        kursor.close();
        return plany;
    }

    public void usunPlan(CPlanowanie pla){
        long id = pla.getPLAk_1_Id();
        dbPLanowanie.delete(CDatabaseManager.TABLE_PLANOWANIE, CDatabaseManager.PLANOWANIE_ID + " = " + id, null);
    }
}
