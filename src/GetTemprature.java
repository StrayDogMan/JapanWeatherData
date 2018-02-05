import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetTemprature {
	private boolean debag = false;

	//初期値
	private String precNoInt = "";//県ごとに割り振られたID
	private String blockNoInt = "";//県の中をブロックごとに振り振られたID

	private Calendar cal = Calendar.getInstance();//カレンダー情報を保持用の変数

	public GetTemprature(String PrecNo, String BlockNo){
		precNoInt = PrecNo;
		blockNoInt = BlockNo;
	}


	//一年分の気温を取得
	//input:year
	//output:年月日時、、降水量、気温、風速
	public String[][] getTemp(int year){
		//データがないときは「///」が入っている
		int allMonthDay =0;
		for(int i =1;i<=12;i++){
			allMonthDay +=getMonthMaxDay(year ,i);
		}
		String returnData[][] = new String[allMonthDay*24][7];

		int count =0;//ID
		String url;//url
		Matcher matcher;
		String precip;//降水量一時保存
		String temp;//温度一時保存
		String wind;//風速一時保存
		for(int i = 0;i<12;i++){//一年分繰り返し
//			monthday = ;//繰り返しのための月の最大日を取得
			for(int j =0;j< getMonthMaxDay(year ,(i+1));j++){//各月の日数
				url ="http://www.data.jma.go.jp/obd/stats/etrn/view/hourly_a1.php?prec_no="+precNoInt+"&block_no="+blockNoInt+"&"
						+ "year="+year
						+ "&month="+(i+1)
						+ "&day="+(j+1)
						+ "&view=";

				List<String> datalist = new Httpcom().getWeb(url);
//				html の103行目から126行目が気象情報
				for(int k = 0; k<24;k++){//24時間分
					if(debag)
						System.out.println(year+","+(i+1)+","+(j+1)+","+(k+1));

//					正規表現を用いて降水量を取得
					precip =datalist.get(102+k).toString().substring(90, 105);
					matcher = Pattern.compile(">[0-9]*[.|0-9]*").matcher(precip);
					if(matcher.find()){
						precip = matcher.group().replace(">", "");
						if(precip ==""){
							precip = "null";
						}
						if(debag)
							System.out.println(precip);
					}else{
						if(debag)
							System.out.println(precip);
						precip = "null";
						if(debag){
							System.out.println("ng");
							System.out.println(precip);
						}
					}

					//正規表現にて気温情報だけ抽出
//					左から130文字目から気温
					temp =datalist.get(102+k).toString().substring(120, 137);
					matcher = Pattern.compile(">.[0-9]*.[0-9]").matcher(temp);
					if(matcher.find()){
						temp = matcher.group().replace(">", "");
						if(debag)
							System.out.println(temp);
					}else{
						if(debag)
							System.out.println(temp);
						temp = "null";
						if(debag){
							System.out.println("ng");
							System.out.println(temp);
						}
					}

					//正規表現にて風速だけ抽出
					wind =datalist.get(102+k).toString().substring(153, 166);
					matcher = Pattern.compile(">[0-9]*[.|0-9]*").matcher(wind);
					if(matcher.find()){
						wind = matcher.group().replace(">", "");
						if(debag)
							System.out.println(wind);
					}else{
						if(debag)
							System.out.println(wind);
						wind = "null";
						if(debag){
							System.out.println("ng");
							System.out.println(wind);
						}
					}

					//戻り値に格納
					returnData[count][0] = Integer.toString(year);//year
					returnData[count][1] = Integer.toString(i+1); //month
					returnData[count][2] = Integer.toString(j+1); //day
					returnData[count][3] = Integer.toString(k+1); //hour
					returnData[count][4] = precip;
					returnData[count][5] = temp;
					returnData[count][6] = wind;

					count++;
				}
			}
		}

		return returnData;
	}

	//get calender
//	指定した年月の日数を取得する
	private int getMonthMaxDay(int year, int month){
		month--;
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
}
