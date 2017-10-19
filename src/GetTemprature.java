import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetTemprature {
	boolean debag = false;

	//初期値
	private String precNoInt = "";
	private String blockNoInt = "";

	public GetTemprature(String PrecNo, String BlockNo){
		precNoInt = PrecNo;
		blockNoInt = BlockNo;
	}

	private Calendar cal = Calendar.getInstance();

	//一年分の気温を取得
	//input:year
	//output:年月日時、気温
	String[][] getTemp(int year){
		//データがないときは「///」が入っている
		int allMonthDay =0;
		for(int i =1;i<=12;i++){
			allMonthDay +=getMonthMaxDay(year ,i);
		}
		String returnData[][] = new String[allMonthDay*24][5];

		int count =0;
		for(int i = 0;i<12;i++){//一年分繰り返し
			int monthday = getMonthMaxDay(year ,(i+1));//繰り返しのための月の最大日を取得
			for(int j =0;j< monthday;j++){//各月の日数
				String url ="http://www.data.jma.go.jp/obd/stats/etrn/view/hourly_a1.php?prec_no="+precNoInt+"&block_no="+blockNoInt+"&"
						+ "year="+year
						+ "&month="+(i+1)
						+ "&day="+(j+1)
						+ "&view=";

//				html の103行目から126行目が気象情報
//				左から130文字目から気温
				List datalist = new Httpcom().getWeb(url);
				String temp;
				Pattern pattern = Pattern.compile(">.*[0-9]<");
				Matcher matcher;
				for(int k = 0; k<24;k++){//24時間分
					//正規表現にて気温情報だけ抽出
					temp =datalist.get(102+k).toString().substring(120, 137);
					matcher = pattern.matcher(temp);
					if(matcher.find()){
						temp = matcher.group();
						switch (temp.length()) {
						case 5:
							temp =temp.substring(1, 4);
							break;
						case 6:
							temp =temp.substring(1, 5);
							break;
						case 7://「 )」などが紛れている場合があるためはじく
							temp =temp.substring(1, 4);
							break;
						case 8://「 )」などが紛れている場合があるためはじく
							temp =temp.substring(1, 5);
							break;
						}
						if(debag)
							System.out.println(year+","+(i+1)+","+(j+1)+","+(k+1)+","+temp);

					}else{
						System.out.println(temp);
						temp = "null";
						if(debag){
							System.out.println("ng");
							System.out.println(year+","+(i+1)+","+(j+1)+","+(k+1)+","+temp);
						}

					}

					returnData[count][0] = Integer.toString(year);//year
					returnData[count][1] = Integer.toString(i+1); //month
					returnData[count][2] = Integer.toString(j+1); //day
					returnData[count][3] = Integer.toString(k+1); //hour
					returnData[count][4] = temp;

					count++;
				}
			}
		}

		return returnData;
	}

	//set calender
	int getMonthMaxDay(int year, int month){
		month--;
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
}
