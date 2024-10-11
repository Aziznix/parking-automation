import java.util.Iterator;
import java.util.Scanner;
import java.time.LocalTime;
import java.security.PublicKey;
import java.time.LocalDate;

/*Kod Notu: 
 * Profili düzenleye liste kodu gelicek listeden istediği işlemleri yapabilecek araç sil-kart ekle-iletisimbilgisi güncelle-
 * eğer iletişim bilgisi yoksa kullanıcıda arac park edemicek eger arac parketmediyse arac park cıkartamıcak
 * markettede eğer kart bilgisi eklemediyse alışveriş yapamayacak
 * 
 * 
 * 
 * araçekleme şuanlık çalışıyor çıktısını profil düzenleden alabiliyoruz geçici süreyle oraya attık 
 * 
 * 
 * kayıtlıMenu sürekli temizlensin diye başına eklentiaraçlar.sleep() attım eğer sıkıntı çıkartırsa ileride çıkartırız
 * --------------------------------------------------------------------------------------------------------------------------------Aziz
 *  bilgi fişi verdiği kısımda ücreti -110 tl olarak hesaplıyor 
 * 
 * 
 * 
 *  hata 101 -Ürün eşleşmemesi
 * */

class KullaniciVeri {
    private String KullaniciAdiString;
    private String KullaniciSifreString;
    
    // Yapıcı metodun adı K
    public KullaniciVeri(String KullaniciAdiString, String KullaniciSifreString) {
        this.KullaniciAdiString = KullaniciAdiString;
        this.KullaniciSifreString = KullaniciSifreString;
    }
    
    // getKullaniciAdiString veri çekmek için alttakide(satır:18-21) aynı şekilde
    public String getKullaniciAdiString() {
        return KullaniciAdiString;
    }
    
    public String getKullaniciSifreString() {
    	return KullaniciSifreString;
    }
}
class araclar {
	void sleeps() {
	try {
	    // 1000 milisaniye (1 saniye) 
	    Thread.sleep(3000);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

	
}
	void clear() {
	for (int i = 0; i < 50; ++i) System.out.println();
	}
}


class kartbilgileri {
	
	private String kartno;
	private String kartskt;
	private String kartcvv;
	
	public kartbilgileri(String kartno,String kartskt,String kartcvv) {
		this.kartno = kartno;
		this.kartskt = kartskt;
		this.kartcvv = kartcvv;
	}
	
	public String getKartno() {
		return kartno;
	}
	public String getKartskt() {
		return kartskt;
	}
	public String getKartcvv() {
		return kartcvv;
	}
	public String toString() {
        return  this.kartno.substring(0,2)+"** **** **** **" + this.kartno.substring(14,16) + "\nSKT " 
	+ this.kartskt + "   " + "CVV " + this.kartcvv; 
    }
    
	
}


class anaMenu{
	
	// Ortak Alan
	KayıtlıMenu kayıtlıMenu = new KayıtlıMenu();
	araclar eklentiAraclar = new araclar(); // 								sleep ve boşluk bırakma komutu
	Scanner AnaMenuScanner = new Scanner(System.in); // 					scan için
	int kullanicisayisi=0; //												kullanıcı sayısı limiti
	KullaniciVeri[] kullanicilar = new KullaniciVeri[100];
	// Kullanıcı sayısını max 100 olması için bu şekilde yazdım 
	//new KullaniciVeri[100] içindekini değiştirerek kullanıcı limitini ayarlayabilirsiniz
	int bulunanIndex = -1;
	Arac[][] aracveri = new Arac[100][5];
	ParkBilgileri[][] parkbilgileri = new ParkBilgileri[100][2];
	kartbilgileri[][] kartbilgileri = new kartbilgileri[100][5];
	Urunler[][] marketUrunler = new Urunler[100][100];
	sepet[] sepets = new sepet[100];
	KisiBilgileri[][] kisiBilgileri = new KisiBilgileri[100][2];
	
	
	
	public int indexbulucu(String kontrolAd, String kontrolSifre) {
        for (int i = 0; i < kullanicisayisi; i++) {
            if (kontrolAd.equals(kullanicilar[i].getKullaniciAdiString()) && 
                kontrolSifre.equals(kullanicilar[i].getKullaniciSifreString())) {
                return i; // Bulunan kullanıcının indisini döndür
            }
        }
        return -1; // Kullanıcı bulunamadıysa -1 döndür
    }
	
	
	void KayitOl() {
	    System.out.println("Kullanici Adi: ");
	    String kullaniciAdi = AnaMenuScanner.nextLine();
	    
	   
	    for (int b = 0; b < kullanicisayisi; b++) {
	        if (kullaniciAdi.equals(kullanicilar[b].getKullaniciAdiString())) {
	            System.err.println("Bu kullanıcı adı kullanılıyor. Lütfen farklı bir kullanıcı adı deneyiniz");
	            eklentiAraclar.sleeps();
	            return; 
	        }
	    }
	    if (kullaniciAdi.equals("Admin") || kullaniciAdi.equals("admin") ) {
            System.err.println("Bu kullanıcı adını alamazsınız!");
            eklentiAraclar.sleeps();
            return; // 
        }
        

	    System.out.println("Kullanici Sifre: ");
	    String kullaniciSifre = AnaMenuScanner.nextLine();
	    System.out.println("Kullanici Kaydi Oluşturuluyor");
	    eklentiAraclar.sleeps();
	    kullanicilar[kullanicisayisi] = new KullaniciVeri(kullaniciAdi, kullaniciSifre);
	    kullanicisayisi++;
	    eklentiAraclar.clear();
	}

	 
	 
	 
	 
	 
	 
	void GirisYap(anaMenu anaMenu) {
		
		anaMenu.marketUrunler[99][99] = new Urunler("Sünger", 10.0, 100, 1);
    	anaMenu.marketUrunler[98][98] = new Urunler("Motor Suyu", 35.5, 100, 2);
    	anaMenu.marketUrunler[97][97] = new Urunler("Motor Yağı", 20.8, 100, 2);
    	anaMenu.marketUrunler[96][96] = new Urunler("Kir Temizleyici", 11.2, 100, 1);
    	
    	
    	
    	
	    /*if(kullanicisayisi == 0) {
	        System.err.println("Şuan sisteme kayitli bir kullanici yok, kayıt olup tekrar deneyiniz!!");
	        eklentiAraclar.sleeps();
	        eklentiAraclar.clear();
	        return;
	    }*/

	    System.out.println("Kullanici Adınızı Giriniz: ");
	    String controlname = AnaMenuScanner.nextLine();
	    System.out.println("Kullanici Sifrenizi Giriniz: ");
	    String controlpassword = AnaMenuScanner.nextLine(); 
	    if(controlname.equals("Admin")&& controlpassword.equals("Admin")) {
	    	System.out.println("Giris Basarili!!");
	        eklentiAraclar.sleeps();
	        eklentiAraclar.clear();
	    	kayıtlıMenu.AdminMenu(anaMenu);
	    	
	    }
	    bulunanIndex = indexbulucu(controlname, controlpassword);
	    if(bulunanIndex != -1) {
	        System.out.println("Giris Basarili!!");
	        eklentiAraclar.sleeps();
	        eklentiAraclar.clear();
	        kayıtlıMenu.Menu(anaMenu);
	    } else {
	    	if(controlname.equals("Admin") == false && controlpassword.equals("Admin")== false){
	    		System.err.println("Hatalı kullanıcı adı veya şifre!\nLütfen tekrar deneyiniz");
	    		return; // Yanlış giriş denemesi olduğunda tekrar giriş yapma işlemine devam etmesi için
	    		
	    	}
	    }
	}
	

}
//Kodun hızlı ve verimli çalışması için bundan sonraki kodları bu satırın altına eklenmesi gerekiyor ///////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class Arac {
    private String plaka;
    private String model;

    public Arac(String plaka, String model) {
        this.plaka = plaka;
        this.model = model;
    }

    public String getPlaka() {
        return plaka;
    }

    public void setPlaka(String plaka) {
        this.plaka = plaka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    //sakın bunu silmeyin yoksa veriyi çekerken başka bir yöntem yazılması gerekiyor
    public String toString() {
        return "Aracın Plakası: " + this.plaka + ", Aracın Modeli: " + this.model;
    }
}


class ParkBilgileri {
    private int parkZaman;
    private int parkTarih;
    private int aracNumara;
    private int indeks;

    public ParkBilgileri(int parkZaman, int parkTarih, int aracNumara, int indeks) {
        this.parkZaman = parkZaman;
        this.parkTarih = parkTarih;
        this.aracNumara = aracNumara;
        this.indeks = indeks;
    }
    

    public int getParkZaman() {
        return parkZaman;
    }

    public int getParkTarih() {
        return parkTarih;
    }
    public int getIndeks() {
    	return indeks;
    }

    public int getAracNumara() {
        return aracNumara;
    }
    
    public String toString() {
        return "Saat: " + this.parkZaman + ", Tarih: " + this.parkTarih + " Arac: "+this.aracNumara;
    }
    

}
class Urunler {
    private String UrunAd;
    private double UrunFiyat;
    private int UrunStok;
    private int UrunBolge;

    public Urunler(String UrunAd, double UrunFiyat, int UrunStok,int UrunBolge) {
        this.UrunAd = UrunAd;
        this.UrunFiyat = UrunFiyat;
        this.UrunStok = UrunStok;
        this.UrunBolge = UrunBolge;
    }

    public String getUrunAd() {
        return UrunAd;
    }

    public double getUrunFiyat() {
        return UrunFiyat;
    }

    public int getstok() {
        return UrunStok;
    }
    
    public int getUrunBolge() {
    	return UrunBolge;
    }

    public void setStock(int UrunStok) {
        this.UrunStok = UrunStok;
    }
  
    public String toString() {
        return "Urun Adı: " + this.UrunAd + "\nUrun Fiyat: " + this.UrunFiyat + "\nUrun Stok: "+this.UrunStok + "\nÜrün Bölgesi: " + UrunBolge+"\n";
    }
}

class sepet{
	int marketsepet;
	
	public sepet(int marketsepet) {
		this.marketsepet = marketsepet;
	}
	public int getMarketSepet() {
		return marketsepet;
	}
	public void setmarketsepet(int marketsepet) {
		this.marketsepet = marketsepet;
	}
	public int toInt() {
		return this.marketsepet;
	}
}

class KisiBilgileri{
	String isim;
	String soyisim;
	String adres;
	String telefon;

	
	public KisiBilgileri(String isim,String soyisim,String adres,String telefon) {
		
		this.isim = isim;
		this.soyisim = soyisim;
		this.adres = adres;
		this.telefon = telefon;
	}
	public String getIsim() {
		return isim;
	}
	public String getSoyisim() {
		return soyisim;
	}
	public String getAdres() {
		return adres;
	}
	public String getTelefon() {
		return telefon;
	}
	public String toString() {
		return  this.isim + " " + this.soyisim + "\nAdres: "+this.adres + "\nTelefon Numarası: " + this.telefon+"\n";
    
		// kullaanıcı adı çıktısı nasıl olsun örnek yazsana .. adres fln yazıcak ya
	}
}







class KayıtlıMenu {
	Scanner kayıtlıMenuScanner = new Scanner(System.in);
	Scanner arabaScanner =  new Scanner(System.in);
	Scanner yetkiliMarketScanner = new Scanner(System.in);
	araclar eklentiAraclar = new araclar(); // 	
	Scanner marketScanner = new Scanner(System.in);
	Scanner adminScanner = new Scanner(System.in);
	int UrunKod = 0;
	double sepettutar =0;
	int sepetadet=0;
	
			
	
	
	void AdminMenu(anaMenu anaMenu) {
		Scanner adminmenuscanner = new Scanner(System.in);
		while (true) {
            System.out.println("---------------------------\n***** Admin Menü *****\n---------------------------");
            System.out.println("[1] Kullanici Menü");
            System.out.println("[2] Araç Menü");
            System.out.println("[3] Market Menü");
            System.out.println("[4] Çıkış");
            System.out.println("---------------------------");
            int adminmenuscan = adminmenuscanner.nextInt();

            switch (adminmenuscan) {
                case 1:
                	KullaniciVeri(anaMenu);
                	//System.out.println("Kullanici Verileri");
                    break;
                case 2:
                	parkaraclar(anaMenu);
                    break;
                case 3:
                	AdmınMarket(anaMenu);
                    break;
                case 4:
                    /*System.out.println("Geriye yönlendiriliyorsunuz..");
                    // sleep kodu gelecek.
                    System.out.print("\033[H\033[2J");*/
                    return; // Programdan çıkış
                default:
                    System.out.println("Geçersiz seçim! Tekrar deneyin.");
            }
        }
	}
	void KullaniciVeri(anaMenu anaMenu) {
		if(anaMenu.kullanicisayisi<1) {
			System.out.println("Şuan Görüntülebilecek herhangi bir kullanıcı yok");
			eklentiAraclar.sleeps();
			return;
		}
		for(int i = 0; i<anaMenu.kullanicisayisi;i++) {
			System.out.println("["+(i+1)+"] Numaralı Kullanıcı\n"+anaMenu.kullanicilar[i].getKullaniciAdiString());
		}
		System.out.println("Ulaşmak istediğiniz kullanıcı numarasını giriniz Çıkış için 0 :");
		int kullanicino = adminScanner.nextInt();
		if(kullanicino==0) {
			return;
		}
		System.out.println("Lütfen Bekleyin");
		eklentiAraclar.sleeps();
		eklentiAraclar.clear();
		System.out.println("Kullanıcı Adı: "+anaMenu.kullanicilar[kullanicino-1].getKullaniciAdiString()+
				"\nŞifre: "+anaMenu.kullanicilar[kullanicino-1].getKullaniciSifreString());
		System.out.println("Kullanıcı Kişisel Bilgiler: \n"+anaMenu.kisiBilgileri[kullanicino-1][0]);
		
	 	if(anaMenu.aracveri[kullanicino-1][0] != null || anaMenu.aracveri[kullanicino-1][1] != null ||anaMenu.aracveri[kullanicino-1][2] != null ||
				anaMenu.aracveri[kullanicino-1][3] != null || anaMenu.aracveri[kullanicino-1][4] != null ) {
			System.out.println("Kişiye ait araçlar\n");
	    for (int j = 0; j < 5; j++) {
	        if (anaMenu.aracveri[kullanicino-1][j] != null) {
	            System.out.println(anaMenu.aracveri[kullanicino-1][j]+"\n");
	        	}
	    	}
		}
		else {
			System.err.println("Kişiye ait araç bulunmamakta \n");
			eklentiAraclar.sleeps();
		}
		System.out.println("\nKullanıcı Kart Bilgileri: ");
        for (int i = 0; i < 5; i++) {
            
                if (anaMenu.kartbilgileri[kullanicino-1][i] != null) {
                    System.out.println(anaMenu.kartbilgileri[kullanicino-1][i]+"\n");
                }
            
        }
        System.out.println("Çıkış yapmak için 0");
        int cikisadmin = adminScanner.nextInt();
        if (cikisadmin==0) {
			return;
		}
        else {
			return;
		}
		
	}
	public void parkaraclar(anaMenu anaMenu) {
	    System.out.println("------Park Halindeki Araçlar------");

	    // Dizilerin boyutlarını kontrol edelim
	    int parkBilgileriBoyutu = anaMenu.parkbilgileri.length;
	    int aracVeriBoyutu = anaMenu.aracveri.length;
	    int kullanicilarBoyutu = anaMenu.kullanicilar.length;

	    for (int i = 0; i < parkBilgileriBoyutu; i++) {
	        for (int j = 0; j < anaMenu.parkbilgileri[i].length; j++) {
	            if (anaMenu.parkbilgileri[i][j] != null) {
	                int indeks = anaMenu.parkbilgileri[i][j].getIndeks();
	                int aracNumara = anaMenu.parkbilgileri[i][j].getAracNumara() - 1;

	                // Dizilerin sınırlarının dışına çıkmadığımızdan emin olalım
	                if (indeks < aracVeriBoyutu && aracNumara < anaMenu.aracveri[indeks].length) {
	                    System.out.println("Park Halindeki Araç:\n" +
	                        anaMenu.aracveri[indeks][aracNumara]);

	                    if (indeks < kullanicilarBoyutu) {
	                        System.out.println("Araç Sahibinin Kullanıcı Adı: " +
	                            anaMenu.kullanicilar[indeks].getKullaniciAdiString() + "\n");
	                    } else {
	                        System.out.println("Geçersiz kullanıcı indeksi: " + indeks);
	                    }
	                } else {
	                    System.out.println("Geçersiz araç indeksi: " + indeks + " veya araç numarası: " + aracNumara);
	                }
	            }
	        }
	    }

	    System.out.println("Çıkış yapmak için 0");
	    Scanner adminScanner = new Scanner(System.in); // adminScanner nesnesini burada oluşturmalısınız.
	    int cikisAdmin = adminScanner.nextInt();

	    if (cikisAdmin == 0) {
	        return;
	    } else {
	        return;
	    }
	}

	void Menu(anaMenu anaMenu) {
		int indeks = anaMenu.bulunanIndex;
		KullaniciVeri[] kullanicilar = new KullaniciVeri[100];
		
		
		
		while (true) {
			eklentiAraclar.clear();
			System.out.println("Gelisim Otopark\n");
			System.out.println("[1]Arac Yönetim");
			System.out.println("[2]Arac Park Et");
			System.out.println("[3]Arac Park Cikar");
			System.out.println("[4]Market");
			System.out.println("[5]Profili Duzenle");
			System.out.println("[6]Cikis");
			int GirisMenuSecim = kayıtlıMenuScanner.nextInt();
			kayıtlıMenuScanner.nextLine();
			
			switch (GirisMenuSecim) {
			case 1:
				 
				aracekle(anaMenu);
				break;
			case 2:
				
				aracparket(anaMenu);
				break;
			case 3:
				aracparkcikar(anaMenu);
				
				break;
			case 4:
				market(anaMenu);
				break;
			case 5:
				profil(anaMenu);
				break;
			case 6:
				System.out.println("Cikis Yapiliyor!!");
				eklentiAraclar.sleeps();
				eklentiAraclar.clear();
				return;
			default:
				System.err.println("Gecersiz secim!");
				break;
			}
		}
	}
	void aracekle(anaMenu anaMenu) {
	    int indeks = anaMenu.bulunanIndex;
	    eklentiAraclar.clear();
	    System.out.println("[1]Araç Ekle");
	    System.out.println("[2]Araç Kaldır");
	    System.out.println("[3]Çıkış");
	    int araceklesecim = arabaScanner.nextInt();
	    arabaScanner.nextLine();
	    switch (araceklesecim) {
	        case 1:
	            if (anaMenu.aracveri[indeks][0] != null && anaMenu.aracveri[indeks][1] != null &&
	                    anaMenu.aracveri[indeks][2] != null &&
	                    anaMenu.aracveri[indeks][3] != null && anaMenu.aracveri[indeks][4] != null) {
	            	System.out.println("Max. Eklenebilecek Araç Sayısı 5 \n");
	                System.out.println("Max Araç Sınırına Ulaştınız!!");
	                eklentiAraclar.sleeps();
	                Menu(anaMenu);
	                return;
	            }
	            System.out.println("Eklemek istediğiniz aracın plakasını giriniz:");
	            String plakaString = arabaScanner.nextLine();
	            System.out.println("Eklemek istediğiniz aracın Modelini giriniz:");
	            String modelString = arabaScanner.nextLine();

	            if (indeks == -1) {
	                System.out.println("Lütfen önce giriş yapın veya kayıt olun!");
	                return;
	            }

	            // Boş bir indeks bulana kadar diziye araç ekleyin
	            int i;
	            for (i = 0; i < 5; i++) {
	                if (anaMenu.aracveri[indeks][i] == null) {
	                    anaMenu.aracveri[indeks][i] = new Arac(plakaString, modelString);
	                    break;
	                }
	            }
	            System.out.println("Araç Başarıyla Kaydedildi araç numaranız:"+(i+1));
	            eklentiAraclar.sleeps();
	            break;
	        case 2:
	        	if (anaMenu.aracveri[indeks][0] == null && anaMenu.aracveri[indeks][1] == null &&
	        	anaMenu.aracveri[indeks][2] == null &&
	        	anaMenu.aracveri[indeks][3] == null && anaMenu.aracveri[indeks][4] == null) {
	        		System.out.println("Kaldırabileceğiniz bir araç bulunmamakta");
	        		eklentiAraclar.sleeps();
	        		return;
	        	}
	            
	            System.out.println("Kaç Numaralı Aracı Kaldırmak İstiyorsunuz?");
	            int kaldirmakIstediğiArac = arabaScanner.nextInt();
	            arabaScanner.nextLine();	            
	            // Kullanıcının kaldırmak istediği araç numarası 1'den başladığı için dizideki indeksle uyumlu hale getirilir
	            int kaldirmakIstediğiAracIndeks = kaldirmakIstediğiArac - 1;
	            
	            // Kullanıcının seçtiği aracı kaldırma
	            if (kaldirmakIstediğiAracIndeks >= 0 && kaldirmakIstediğiAracIndeks < 5) {
	                System.out.println("Arac Basarıyla Kaldırıldı");
	                anaMenu.aracveri[indeks][kaldirmakIstediğiAracIndeks] = null;

	                // Kaldırılan araçtan sonraki diğer araçların kaydırılması için
	                for (int a = kaldirmakIstediğiAracIndeks; a < 4; a++) {
	                    anaMenu.aracveri[indeks][a] = anaMenu.aracveri[indeks][a + 1];
	                }
	                // Son araç null olarak ayarlanıyorki tekrardan araç eklenmek istediğinde sıkıntı çıkarmasın
	                anaMenu.aracveri[indeks][4] = null;
	                eklentiAraclar.sleeps();
	            } else {
	                System.out.println("Geçersiz araç numarası!");
	            }

	            break;

	        case 3:
	            System.out.println("Çıkış Yapılıyor...");
	            Menu(anaMenu);
	            return; 
	        default:
	            System.out.println("Geçersiz seçim!");
	            break;
	    }
	}
	void aracparket(anaMenu anaMenu) {
		int indeks = anaMenu.bulunanIndex;
		LocalTime parkZaman = LocalTime.now();
        LocalDate parkTarih = LocalDate.now();
        
        int hour = parkZaman.getHour();
        int day = parkTarih.getDayOfYear();
        int year =parkTarih.getYear();
        
        if (anaMenu.aracveri[indeks][0] == null && anaMenu.aracveri[indeks][1] == null &&
	        	anaMenu.aracveri[indeks][2] == null &&
	        	anaMenu.aracveri[indeks][3] == null && anaMenu.aracveri[indeks][4] == null) {
	        		System.err.println("Park edebileceğiniz bir araç bulunmamakta");
	        		eklentiAraclar.sleeps();
	        		return;
	        	}
        if(anaMenu.parkbilgileri[indeks][indeks]!= null) {
        	System.err.println("Max park etme sınırına ulaştınız lütfen \nparktaki aracınızı çıkardıktan sonra tekrar deneyin");
        	eklentiAraclar.sleeps();
        }
        else {
        	System.out.println("Park etmek istediğiniz aracın numarasını giriniz!");
        	int aracnum = arabaScanner.nextInt();
        	if (anaMenu.aracveri[indeks][aracnum-1] == null) {
				System.err.println("Araç bulunamadı lütfen tekrar deneyiniz!");
				eklentiAraclar.sleeps();
				return;
			}
        	
        	anaMenu.parkbilgileri[indeks][indeks] = new ParkBilgileri(hour, day, aracnum, indeks);
        	System.out.println("Araç başarıyla park edildi!!");
        	eklentiAraclar.sleeps();
        }
        System.out.println(anaMenu.parkbilgileri[indeks][0]);
    }
    void aracparkcikar(anaMenu anaMenu) {


        	int indeks = anaMenu.bulunanIndex;
        	LocalTime parkZamanC = LocalTime.now();
            LocalDate parkTarihC = LocalDate.now();
            
            if (anaMenu.aracveri[indeks][0] == null && anaMenu.aracveri[indeks][1] == null &&
    	        	anaMenu.aracveri[indeks][2] == null &&
    	        	anaMenu.aracveri[indeks][3] == null && anaMenu.aracveri[indeks][4] == null) {
    	        		System.err.println("Bu işlemi gerçekleştirmeden önce araç eklemeniz gerekmekte!");
    	        		eklentiAraclar.sleeps();
    	        		return;
    	        	}
            if(anaMenu.parkbilgileri[indeks][indeks] == null) {
            	System.err.println("Park edilmiş bir araç bulunmamakta");
            	eklentiAraclar.sleeps();
            	return;
            }
            
            
        	
        	int saat = parkZamanC.getHour();
        	int tarih= parkTarihC.getDayOfYear();       
        	int toplamGun = anaMenu.parkbilgileri[indeks][indeks].getParkTarih() - tarih;
        	int toplamSaat = anaMenu.parkbilgileri[indeks][indeks].getParkZaman() - saat+1; // saat +1 olmasının sebebi kullanıcı otoparka girip çıksa bile ücret istenmesi
        	
        	
        	if (toplamGun < 0 || toplamSaat <= 0) {
                System.out.println("Geçersiz park bilgileri!");
                return;
            }

            
            int gunUcreti = toplamGun * 202; // 202 günlük ücreti
            int saatUcreti = toplamSaat * 52; // 12 saatten sonra her saat için 52₺
            int toplamUcret = gunUcreti + saatUcreti;
            
          

          
           System.out.println("Toplam Geçirilen Zaman: " + toplamSaat + " Saat " + toplamGun + " Gün\nPark Ücreti: " + toplamUcret + "₺");
            
            
            
            
            
            //-----------------------------------------------------------------------------------------------------
           /*
        	
            String kartn = "4543180038765588";   // 487-490.satırlar deneme için 
            String kartskString = "11/28";
            String kartcvString = "733";
            anaMenu.kartbilgileri[indeks][0] = new kartbilgileri(kartn, kartskString, kartcvString);
            String kartn1 = "2343180038765566";   // 487-490.satırlar deneme için 
            String kartskString1 = "07/23";
            String kartcvString1 = "881";
            anaMenu.kartbilgileri[indeks][1] = new kartbilgileri(kartn1, kartskString1, kartcvString1);

            String kartn2 = "1143180038765590";   // 487-490.satırlar deneme için 
            String kartskString2 = "01/32";
            String kartcvString2 = "329";
            anaMenu.kartbilgileri[indeks][2] = new kartbilgileri(kartn2, kartskString2, kartcvString2);
            */
            //-----------------------------------------------------------------------------------------------------
            
            
            
            
            
            if(anaMenu.kartbilgileri[indeks][indeks] == null) {
            	System.err.println("Ödeme yapabileceğiniz bir kart bulunmamaktadır lütfen kart ekledikten sonra tekrar deneyiniz!");
            	eklentiAraclar.sleeps();
            	return;
            }
            
    
            
            for (int j = 0; j < 5; j++) {
		        if (anaMenu.kartbilgileri[indeks][j] != null) {
		        	System.out.println("\n        ["+(j+1)+"]");
		            System.out.println(anaMenu.kartbilgileri[indeks][j]);
		        }
		    }
            System.out.println("\n\nLütfen ödeme yapmak istediğiniz kartı seçiniz:");
            int kartscan = arabaScanner.nextInt();
            
            System.out.println(anaMenu.kartbilgileri[indeks][kartscan-1]);
            System.out.println("\nSeçilen kart ile ödeme yapılıyor");
            eklentiAraclar.sleeps();
            System.out.println("\nLütfen Bekleyin!");
            eklentiAraclar.sleeps();
            System.out.println("\nÖdeme Onaylandı İyi Günler \n\n");
            eklentiAraclar.sleeps();
            // Bilgi fişi yazdırılır
            System.out.println("-----------[Bilgi Fişi]----------- ");
            System.out.println("Toplam Geçirilen Zaman: " + toplamSaat + " Saat " + toplamGun + " Gün\nPark Ücreti: " + toplamUcret + "₺\n"
            		+ "Park Edilen Araç \n" + anaMenu.aracveri[indeks][anaMenu.parkbilgileri[indeks][indeks].getAracNumara()-1] );
            eklentiAraclar.sleeps();
            
            anaMenu.parkbilgileri[indeks][indeks] = null;

    }
    void AdmınMarket(anaMenu anaMenu) { 
    	eklentiAraclar.clear();
        System.out.println("---------------------------\n***** Gelisim Otopark *****\n---------------------------");
        System.out.println("[1] Urun Ekleme");
        System.out.println("[2] Urun Kaldirma");
        System.out.println("[3] Urun Listeleme");
        System.out.println("[4] Stok Guncelleme");
        System.out.println("[5] Çıkış");
        System.out.println("---------------------------");
    	int abc = yetkiliMarketScanner.nextInt();
    	
    	switch (abc) {
		case 1:
			UrunEklemeAdmin(anaMenu);
			break;
		case 2:
			UrunKaldırmaAdmin(anaMenu);
			break;
		case 3:
			UrunListelemeAdmin(anaMenu);
			break;
		case 4: 
			UrunStokGuncellemeAdmin(anaMenu);
			break;
		case 5:
			break;
		default:
			System.out.println("Hatalı seçim!");
			eklentiAraclar.sleeps();
			break;
		}
    }
    void UrunEklemeAdmin(anaMenu anaMenu) { 
    	Scanner urunisimscanner = new Scanner(System.in);
    	eklentiAraclar.clear();
    	System.out.println("[1]Kozmetik\n[2]Bakım\n");
    	System.out.println("Eklemek İstediğiniz Ürünün Bölgesi: ");
    	int urunBolgeInt = yetkiliMarketScanner.nextInt();
    	System.out.println("Eklemek İstediğiniz Ürünün İsmi");
    	String urunAdString = urunisimscanner.nextLine();
    	System.out.println("Eklemek İstediğiniz Ürünün Fiyatı: ");
    	double urunFiyatDouble = yetkiliMarketScanner.nextDouble();
    	System.out.println("Eklemek İstediğiniz Ürünün Adedi: ");
    	int urunStokInt = yetkiliMarketScanner.nextInt();
    	System.out.println("Urununuz Başarıyla Eklendi!!!\nUrun Kodu: "+ (UrunKod+1));
    	
    	anaMenu.marketUrunler[UrunKod][UrunKod] = new Urunler(urunAdString, urunFiyatDouble, urunStokInt, urunBolgeInt); 
    	UrunKod++;
    	eklentiAraclar.sleeps();
    	eklentiAraclar.clear();
    	
    }
    void UrunKaldırmaAdmin(anaMenu anaMenu) {
    	eklentiAraclar.clear();
        System.out.println("[1]Kozmetik\n[2]Bakım\n");
        System.out.println("Kaldırmak İstediğiniz Ürün Bölgesi:");
        int urunBolgeInt = yetkiliMarketScanner.nextInt();
        System.out.println("Kaldırmak istediğiniz ürünün Kodu: ");
        int UrunKodSorgu = yetkiliMarketScanner.nextInt();

        if (anaMenu.marketUrunler[UrunKodSorgu - 1][UrunKodSorgu - 1].getUrunBolge() == urunBolgeInt) {
            System.out.println(anaMenu.marketUrunler[UrunKodSorgu - 1][UrunKodSorgu - 1]);

            
            for (int i = UrunKodSorgu - 1; i < anaMenu.marketUrunler.length - 1; i++) {
                anaMenu.marketUrunler[i][UrunKodSorgu - 1] = anaMenu.marketUrunler[i + 1][UrunKodSorgu - 1];
            }

            anaMenu.marketUrunler[anaMenu.marketUrunler.length - 1][UrunKodSorgu - 1] = null;

            System.out.println("Ürün Başarıyla Kaldırıldı");
            eklentiAraclar.sleeps();
            eklentiAraclar.clear();
        }
    }
    void UrunStokGuncellemeAdmin(anaMenu anaMenu) {
    	eklentiAraclar.clear();
        System.out.println("Stok bilgisini güncellemek istediğiniz ürünün kodu: ");
        int UrunKodSorgu = yetkiliMarketScanner.nextInt();

        
            System.out.println(anaMenu.marketUrunler[UrunKodSorgu - 1][UrunKodSorgu - 1]+"\n");

            
           System.out.println("Güncel stok miktarını giriniz: ");
           int güncelstok = yetkiliMarketScanner.nextInt();
           
           anaMenu.marketUrunler[UrunKodSorgu-1][UrunKodSorgu-1].setStock(güncelstok);
            

            System.out.println("Stok Başarıyla Güncellendi!\n");
            System.out.println(anaMenu.marketUrunler[UrunKodSorgu - 1][UrunKodSorgu - 1]);
            eklentiAraclar.sleeps();
            eklentiAraclar.clear();
        
    
    }
    void UrunListelemeAdmin(anaMenu anaMenu) {
    	eklentiAraclar.clear();
    	System.out.println("----------Ürünler----------\n");
        for (int i = 0; i < 100; i++) {
            if (anaMenu.marketUrunler[i][i] != null) {
            	System.out.println(anaMenu.marketUrunler[i][i].getUrunAd() +"\nStok:"+anaMenu.marketUrunler[i][i].getstok()+ "\nFiyat: " + anaMenu.marketUrunler[i][i].getUrunFiyat()
                        + "₺\n" + "Urun Kodu: " + (i + 1)+"\n");
            }
        }
        System.out.println("\n\nÇıkış yapmak için 0:");
        int sepet1 = marketScanner.nextInt();
        if(sepet1==0) {
        	
        	eklentiAraclar.clear();
        	return;
        }
        else {
			eklentiAraclar.clear();
			return;
		}
    }
    void UrunListelemeKozmetik(anaMenu anaMenu) {
        System.out.println("----------Ürünler----------\n");
        for (int i = 0; i < 100; i++) {
            if (anaMenu.marketUrunler[i][i] != null && anaMenu.marketUrunler[i][i].getUrunBolge() == 1) {
                System.out.println(anaMenu.marketUrunler[i][i].getUrunAd() + "\nFiyat: " + anaMenu.marketUrunler[i][i].getUrunFiyat()
                        + "₺\n" + "Urun Kodu: " + (i + 1)+"\n");
            }
        }
        System.out.println("\n\nSepete Ürün eklemek için 1 ; çıkış için 2 ");
        int sepet1 = marketScanner.nextInt();
        if (sepet1 == 1) {
            System.out.println("Kaç adet ürün eklemek istiyorsunuz");
            int adet = marketScanner.nextInt();
            for (int i = 0; i < adet; i++) {
                System.out.println("Sepete Eklemek İstediğiniz ürünlerin kodunu yazın: ");
                int sepet = marketScanner.nextInt();
                anaMenu.sepets[sepetadet]= new sepet(sepet-1);
                sepetadet++;
                System.out.println("Ürün Başarıyla Eklendi");
                eklentiAraclar.sleeps();
                if (sepet<101 && anaMenu.marketUrunler[sepet - 1][sepet - 1] != null && anaMenu.marketUrunler[sepet-1][sepet-1].getUrunBolge()==1 ) {
                    sepettutar = sepettutar + anaMenu.marketUrunler[sepet - 1][sepet - 1].getUrunFiyat();
                } else {
                    System.out.println("Geçersiz ürün kodu! Lütfen doğru bir ürün kodu girin.");
                    eklentiAraclar.sleeps();
                    i--; // 
                }
            }
        } else {
            return;
        }
    }
    void UrunListelemeBakım(anaMenu anaMenu) {
    	System.out.println("----------Ürünler----------\n");
        for (int i = 0; i < 100; i++) {
            if (anaMenu.marketUrunler[i][i] != null && anaMenu.marketUrunler[i][i].getUrunBolge() == 2) {
                System.out.println(anaMenu.marketUrunler[i][i].getUrunAd() + "\nFiyat: " + anaMenu.marketUrunler[i][i].getUrunFiyat()
                        + "₺\n" + "Urun Kodu: " + (i + 1)+"\n");
            }
        }
        System.out.println("\n\nSepete Ürün eklemek için 1 ; çıkış için 2 ");
        int sepet1 = marketScanner.nextInt();
        if (sepet1 == 1) {
            System.out.println("Kaç adet ürün eklemek istiyorsunuz");
            int adet = marketScanner.nextInt();
            for (int i = 0; i < adet; i++) {
                System.out.println("Sepete Eklemek İstediğiniz ürünlerin kodunu yazın: ");
                int sepet = marketScanner.nextInt();
                anaMenu.sepets[sepetadet]= new sepet(sepet-1);
                sepetadet++;
                System.out.println("Ürün Başarıyla Eklendi");
                eklentiAraclar.sleeps();
                if (sepet<101 && anaMenu.marketUrunler[sepet - 1][sepet - 1] != null && anaMenu.marketUrunler[sepet-1][sepet-1].getUrunBolge()==2 ) {
                    sepettutar = sepettutar + anaMenu.marketUrunler[sepet - 1][sepet - 1].getUrunFiyat();
                    
                } else {
                    System.out.println("Geçersiz ürün kodu! Lütfen doğru bir ürün kodu girin.");
                    eklentiAraclar.sleeps();
                    i--; // 
                }
            }
        } else {
            return;
        }

    }
    void sepet(anaMenu anaMenu) {
        System.out.println("-------Sepetteki Ürünler--------\n");
        double sepettutar = 0.0; // Sepet toplam tutarını başlatıyoruz
        for (int i = 0; i < anaMenu.sepets.length; i++) {
        
            if (anaMenu.sepets[i] != null) {
                int sepetIndex = anaMenu.sepets[i].getMarketSepet(); // Tek indeks kullanıyoruz
                String urunAd = anaMenu.marketUrunler[sepetIndex][sepetIndex].getUrunAd();
                double urunFiyat = anaMenu.marketUrunler[sepetIndex][sepetIndex].getUrunFiyat();
                System.out.println((i+1) + "|" + urunAd + " " + urunFiyat + "₺\n");
                sepettutar+=urunFiyat;
                
            }
        }
        
        System.out.println("Toplam Sepet Tutarı: " + sepettutar + "₺");
        eklentiAraclar.sleeps();
        eklentiAraclar.sleeps();
        eklentiAraclar.sleeps();
    }
    void market(anaMenu anaMenu) {
    	int indeks = anaMenu.bulunanIndex;
    	
    	
    	Scanner marketScanner = new Scanner(System.in);
    	System.out.println("[1]Kozmatik Ürünleri");
    	System.out.println("[2]Bakım ürünleri");
    	System.out.println("[3]Sepet");
    	System.out.println("[4]Ödeme Yap");
    	int marketsecim = marketScanner.nextInt();
    	//"₺"
    	switch (marketsecim) {
		case 1:
			UrunListelemeKozmetik(anaMenu);
			break;
		case 2:
			UrunListelemeBakım(anaMenu);
			break;
		case 3:
			//UrunEklemeAdmin(anaMenu);
			sepet(anaMenu);
			break;
		case 4: 
			if(anaMenu.sepets[0]==null) {
				System.out.println("Lütfen önce sepetinize ürün ekleyin");
				eklentiAraclar.sleeps();
				return;
			}
			if(anaMenu.kartbilgileri[indeks][indeks] == null) {
            	System.err.println("Ödeme yapabileceğiniz bir kart bulunmamaktadır lütfen kart ekledikten sonra tekrar deneyiniz!");
            	
            	eklentiAraclar.sleeps();
            	return;
            }
            
    
            
            for (int j = 0; j < 5; j++) {
		        if (anaMenu.kartbilgileri[indeks][j] != null) {
		        	System.out.println("\n        ["+(j+1)+"]");
		            System.out.println(anaMenu.kartbilgileri[indeks][j]);
		        }
		    }
            System.out.println("\n\nLütfen ödeme yapmak istediğiniz kartı seçiniz:");
            int kartscan = arabaScanner.nextInt();
            
            System.out.println(anaMenu.kartbilgileri[indeks][kartscan-1]);
            System.out.println("\nSeçilen kart ile ödeme yapılıyor");
            eklentiAraclar.sleeps();
            System.out.println("\nLütfen Bekleyin!");
            eklentiAraclar.sleeps();
            System.out.println("\nÖdeme Onaylandı İyi Günler \n\n");
            eklentiAraclar.sleeps();
            // Bilgi fişi yazdırılır
            System.out.println("-----------[Bilgi Fişi]----------- ");
            for (int i = 0; i < anaMenu.sepets.length; i++) {
                // Null kontrolü ekliyoruz
                if (anaMenu.sepets[i] != null) {
                    int sepetIndex = anaMenu.sepets[i].getMarketSepet(); // Tek indeks kullanıyoruz
                    String urunAd = anaMenu.marketUrunler[sepetIndex][sepetIndex].getUrunAd();
                    double urunFiyat = anaMenu.marketUrunler[sepetIndex][sepetIndex].getUrunFiyat();
                    System.out.println((i+1) + "|" + urunAd + " " + urunFiyat + "₺\n");
                     // Sepet tutarına ekleme yapıyoruz
                }
            }
            System.out.println("Toplam Alışveriş Tutarı: " + sepettutar + "₺");
            eklentiAraclar.sleeps();
            for(int i = 0;i<100;i++) {
            	anaMenu.sepets[i] = null;
            	sepettutar=0;
            }
			break;
		default:
			break;
		}
    	
    }
    void kartBilgi(anaMenu anaMenu) {
    	 eklentiAraclar.clear();
        Scanner profilkartScanner = new Scanner(System.in);

        System.out.println("---------------------------\n***** Gelisim Otopark *****\n---------------------------");
        System.out.println("[1] Kartlarımı Görüntüle");
        System.out.println("[2] Kart Ekle");
        System.out.println("[3] Kart Sil");
        System.out.println("[4] Geri");
        System.out.println("---------------------------");
        int kartsecim = profilkartScanner.nextInt();

        switch (kartsecim) {
            case 1:
                // Kartları göster
                kartlariGoruntule(anaMenu);
                kartBilgi(anaMenu); // Kartları görüntüledikten sonra tekrar kart bilgileri menüsüne dön
                break;
            case 2:
                // Kart ekle
                kartEkle(anaMenu);
                kartBilgi(anaMenu); // Kart ekledikten sonra tekrar kart bilgileri menüsüne dön
                break;
            case 3:
                // Kart sil
                kartSil(anaMenu);
                kartBilgi(anaMenu); // Kart sildikten sonra tekrar kart bilgileri menüsüne dön
                break;
            case 4:
                // Geri dön
                return;
            default:
                System.out.println("Geçersiz seçim! Tekrar deneyin.");
                kartBilgi(anaMenu); // Hatalı seçim durumunda tekrar kart bilgileri menüsüne dön
        }
    }
    void kartlariGoruntule(anaMenu anaMenu) {
    	int indeks = anaMenu.bulunanIndex;
    	eklentiAraclar.clear();
        boolean kartVarMi = false;
        for (int i = 0; i <5; i++) {
            
                if (anaMenu.kartbilgileri[indeks][i] != null) {
                    kartVarMi = true;
                    break;
                }
            
        }

        if (!kartVarMi) {
            System.out.println("Henüz hiç kart eklenmemiş!");
            eklentiAraclar.sleeps();
            return;
        }

        System.out.println("Kart Bilgileriniz:");
        for (int i = 0; i < 5; i++) {
            
                if (anaMenu.kartbilgileri[indeks][i] != null) {
                    System.out.println(anaMenu.kartbilgileri[indeks][i]);
                }
            
        }
    }
    void kartEkle(anaMenu anaMenu) {
    	 eklentiAraclar.clear();
    	int indeks = anaMenu.bulunanIndex;
        Scanner scanner = new Scanner(System.in);

        // Kart bilgilerini al
        System.out.println("Eklemek istediğiniz kartın kart numarasını giriniz (Maks 16):");
        String kartNo = scanner.nextLine();
        if(kartNo.length()<16) {
        	System.err.println("Hatalı kart numarası lütfen geçerli bir kart numarası giriniz!");
        	return;
        }
        System.out.println("Eklemek istediğiniz kartın son tarihini giriniz: ");
        String sonTarih = scanner.nextLine();
        System.out.println("Eklemek istediğiniz kartın CVV numarasını giriniz: ");
        String cvv = scanner.nextLine();
        
        for (int i = 0; i < 5; i++) {
            
                if (anaMenu.kartbilgileri[indeks][i] == null) {
                	anaMenu.kartbilgileri[indeks][i] = new kartbilgileri(kartNo, sonTarih, cvv);
                    System.out.println("Kart başarıyla eklendi.");
                    eklentiAraclar.sleeps();
                    return; // Kart eklendikten sonra işlemi bitiriyor
                }
            
        }

        System.out.println("Maksimum kart sayısına ulaşıldı!");
        eklentiAraclar.sleeps();
    }
    void kartSil(anaMenu anaMenu) {
    	 eklentiAraclar.clear();
    	Scanner kartsilScanner = new Scanner(System.in);
    	int indeks = anaMenu.bulunanIndex;
        System.out.println("Kaldırmak istediğiniz ürünün Kodu: ");
        int UrunKodSorgu = kartsilScanner.nextInt();

 

            
            for (int i = UrunKodSorgu - 1; i < 5-UrunKodSorgu; i++) {
                anaMenu.kartbilgileri[indeks][UrunKodSorgu - 1] = anaMenu.kartbilgileri[indeks][UrunKodSorgu];
            }

            anaMenu.kartbilgileri[indeks][5-UrunKodSorgu] = null;
            

            System.out.println("Ürün Başarıyla Kaldırıldı");
            eklentiAraclar.sleeps();
        
    }
    void bilgiler(anaMenu anaMenu) {
        Scanner profilbilgiScanner = new Scanner(System.in);
        int indeks = anaMenu.bulunanIndex;
        eklentiAraclar.clear();
        

        if (anaMenu.kisiBilgileri[indeks][0]== null) {
            // Bilgiler daha önce doldurulmadıysa
            System.out.println("İsminizi giriniz: ");
            String KayıtIsim = profilbilgiScanner.nextLine();
            System.out.println("Soyisminizi giriniz: ");
            String KayıtSoyisim = profilbilgiScanner.nextLine();
            System.out.println("Adres giriniz: ");
            String KayıtAdres = profilbilgiScanner.nextLine();
            System.out.println("Telefon numaranızı giriniz: ");
            String KayıtTelefon = profilbilgiScanner.nextLine();
            anaMenu.kisiBilgileri[indeks][0] = new KisiBilgileri(KayıtIsim, KayıtSoyisim, KayıtAdres, KayıtTelefon);
           
            System.out.println("Bilgileriniz başarıyla kaydedildi.");
            eklentiAraclar.sleeps();
        } else {
            // Bilgiler daha önce doldurulduysa, kullanıcıya mevcut bilgilerini göster ve güncelleme seçeneği sun
            System.out.println(anaMenu.kisiBilgileri[indeks][0]);

            System.out.println("Bilgilerinizi güncellemek istiyor musunuz? (Evet: 1 / Hayır: 0)");
            int cevap = profilbilgiScanner.nextInt();
            profilbilgiScanner.nextLine(); // Boş satırı tüket

            if (cevap == 1) {
                // Bilgileri güncelle
            	 System.out.println("İsminizi giriniz: ");
                 String KayıtIsim = profilbilgiScanner.nextLine();
                 System.out.println("Soyisminizi giriniz: ");
                 String KayıtSoyisim = profilbilgiScanner.nextLine();
                 System.out.println("Adres giriniz: ");
                 String KayıtAdres = profilbilgiScanner.nextLine();
                 System.out.println("Telefon numaranızı giriniz: ");
                 String KayıtTelefon = profilbilgiScanner.nextLine();
                 anaMenu.kisiBilgileri[indeks][0] = new KisiBilgileri(KayıtIsim, KayıtSoyisim, KayıtAdres, KayıtTelefon);

                System.out.println("Bilgileriniz başarıyla güncellendi.");
                eklentiAraclar.sleeps();
            } else {
                System.out.println("Bilgiler güncellenmedi.");
                eklentiAraclar.sleeps();
            }
        }
    }  
    void profil(anaMenu anaMenu) {
    	eklentiAraclar.clear();
    	int indeks = anaMenu.bulunanIndex;
    	Scanner profilduzenleScanner = new Scanner(System.in);
    	while (true) {
            System.out.println("---------------------------\n***** Gelisim Otopark *****\n---------------------------");
            System.out.println("[1] Arac Listem");
            System.out.println("[2] Kart Bilgilerim");
            System.out.println("[3] Bilgilerim");
            System.out.println("[4] Çıkış");
            System.out.println("---------------------------");
            int profilmenususecim = profilduzenleScanner.nextInt();

            switch (profilmenususecim) {
                case 1:
                	if(anaMenu.aracveri[indeks][0] != null || anaMenu.aracveri[indeks][1] != null ||anaMenu.aracveri[indeks][2] != null ||
					anaMenu.aracveri[indeks][3] != null || anaMenu.aracveri[indeks][4] != null ) {
				System.out.println("Kişiye ait araçlar\n");
		    for (int j = 0; j < 5; j++) {
		        if (anaMenu.aracveri[indeks][j] != null) {
		            System.out.println("["+(j+1)+"]Arac\n" + anaMenu.aracveri[indeks][j]+"\n");
		        	}
		    	}
			}
			else {
				System.err.println("Kişiye ait araç bulunmamakta \n");
				eklentiAraclar.sleeps();
			}
                	
                    break;
                case 2:
                    kartBilgi(anaMenu);
                   
                    break;
                case 3:
                    bilgiler(anaMenu);
                   
                    break;
                case 4:
                    System.out.println("Geriye yönlendiriliyorsunuz..");
                    // sleep kodu gelecek.
                    System.out.print("\033[H\033[2J");
                    return; // Programdan çıkış
                default:
                    System.out.println("Geçersiz seçim! Tekrar deneyin.");
            }
        }
    }

}



public class main {

	public static void main(String[] args) {
		
		Scanner mainScanner = new Scanner(System.in);
		 anaMenu anaMenu = new anaMenu();
		while(true) {
			System.out.println("Gelisim Otopark");
            System.out.println("[1] Kayit Ol");
            System.out.println("[2] Giris Yap");
            System.out.println("[3] Cikis Yap");
            System.out.println("Islem Giriniz: ");
            int m = mainScanner.nextInt();
            mainScanner.nextLine(); 
            
            switch(m){
                case 1: 
                	anaMenu.KayitOl();
                    break;
                case 2:
                    anaMenu.GirisYap(anaMenu);
                    break;
                case 3: 
                    System.exit(0);
                    break;
                default:
                    System.out.println("Gecersiz secim!");
                    break;
            }
		}
	}
}

