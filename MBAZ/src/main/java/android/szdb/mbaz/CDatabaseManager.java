package android.szdb.mbaz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CDatabaseManager extends SQLiteOpenHelper{
    //OKRES
    public static final String TABLE_OKRES = "OKRES";
    public static final String OKRES_ID = "OKRk_1_Id";
    public static final String OKRES_OD = "OKR_Od";
    public static final String OKRES_DO = "OKR_Do";

    //DOCHODY
    public static final String TABLE_DOCHODY = "DOCHODY";
    public static final String DOCHODY_ID = "DOCk_1_Id";
    public static final String DOCHODY_KWOTA = "DOC_Kwota";
    public static final String DOCHODY_DATA = "DOC_Data";
    public static final String OKRES_ID_FK_DOCHODY = "OKR_Id";
    public static final String KDO_ID_FK_DOCHODY= "KDO_Id";

    //KATEGORIE DOCHODÓW
    public static final String TABLE_KDO = "KATEGORIE_DOCHODÓW";
    public static final String KDO_ID = "KDOk_1_Id";
    public static final String KDO_NAZWA = "KDO_Nazwa";

    //WYDATKI
    public static final String TABLE_WYDATKI = "WYDATKI";
    public static final String WYDATKI_ID = "WYDk_1_Id";
    public static final String WYDATKI_KWOTA = "WYD_Kwota";
    public static final String WYDATKI_DATA = "WYD_Data";
    public static final String OKRES_ID_FK_WYDATKI = "OKR_Id";
    public static final String KWY_ID_FK_WYDATKI = "KWY_Id";
    public static final String SUB_ID_FK_WYDATKI = "SUB_Id";

    //KATEGORIE WYDATKÓW
    public static final String TABLE_KWY = "KATEGORIE_WYDATKÓW";
    public static final String KWY_ID = "KWYk_1_Id";
    public static final String KWY_NAZWA = "KWY_Nazwa";

    //SUBKATEGORIA
    public static final String TABLE_SUB = "SUBKATEGORIA";
    public static final String SUB_ID = "SUBk_1_Id";
    public static final String SUB_NAZWA = "SUB_Nazwa";

    //Tworzenie bazy
    private static final String DB_NAZWA = "SZDM.db";

    //Tworzenie TABEL
    private static final String CREATE_TABLE_OKRES = "CREATE TABLE " + TABLE_OKRES + "(" + OKRES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + OKRES_OD + " TEXT," + OKRES_DO + " TEXT" + ")";
    private static final String CREATE_TABLE_KDO = "CREATE TABLE " + TABLE_KDO + "(" + KDO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KDO_NAZWA + " TEXT" + ")";
    private static final String CREATE_TABLE_DOCHODY = "CREATE TABLE " + TABLE_DOCHODY + "(" + DOCHODY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + DOCHODY_KWOTA + " REAL," + DOCHODY_DATA + " DATE," + KDO_ID_FK_DOCHODY + " INTEGER NOT NULL, FOREIGN KEY(" + KDO_ID_FK_DOCHODY + ")" + "REFERENCES " + TABLE_KDO + "(" + KDO_ID + "), " + OKRES_ID_FK_DOCHODY + " INTEGER NOT NULL, FOREIGN KEY(" + OKRES_ID_FK_DOCHODY + ")" + "REFERENCES " + TABLE_OKRES + "(" + OKRES_ID + ")" + ")";
    private static final String CREATE_TABLE_KWY = "CREATE TABLE " + TABLE_KWY + "(" + KWY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KWY_NAZWA + " TEXT" + ")";
    private static final String CREATE_TABLE_WYDATKI = "CREATE TABLE " + TABLE_WYDATKI + "(" + WYDATKI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + WYDATKI_KWOTA + " REAL," + WYDATKI_DATA + " TEXT," + KWY_ID_FK_WYDATKI + " INTEGER NOT NULL, FOREIGN KEY(" + KWY_ID_FK_WYDATKI + ")" + "REFERENCES " + TABLE_KWY + "(" + KWY_ID + "), " + SUB_ID_FK_WYDATKI + "INTEGER NOT NULL, FOREIGN KEY(" + SUB_ID_FK_WYDATKI + ")" + "REFERENCES " + TABLE_SUB + "(" + SUB_ID + "), " + OKRES_ID_FK_WYDATKI + " INTEGER NOT NULL, FOREIGN KEY(" + OKRES_ID_FK_WYDATKI + ")" + "REFERENCES " + TABLE_OKRES + "(" + OKRES_ID + ")" + ")";
    private static final String CREATE_TABLE_SUBKATEGORIA = "CREATE TABLE " + TABLE_SUB + "(" + SUB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + SUB_NAZWA + " TEXT" + ")";

    //USTALENIE WERSJI - nie zmieniamy
    private static final int DB_WERSJA = 1;

    public CDatabaseManager(Context context) {
        super(context, DB_NAZWA, null, DB_WERSJA);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_TABLE_OKRES);
        db.execSQL(CREATE_TABLE_KWY);
        db.execSQL(CREATE_TABLE_WYDATKI);
        db.execSQL(CREATE_TABLE_SUBKATEGORIA);
        db.execSQL(CREATE_TABLE_KDO);
        db.execSQL(CREATE_TABLE_DOCHODY);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OKRES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KDO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCHODY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KWY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WYDATKI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUB);

        onCreate(db);
    }
}
