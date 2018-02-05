public class TempratureAnalysis {
	static boolean debag = false;
	//1978
	static int startYear = 1978;
	static int endYear = 1978;

	//埼玉県さいたま市のユニークナンバー
	static String precNo = "43";
	static String blockNo = "0363";


	public static void main(String args[]){
		long start = System.currentTimeMillis();

		//1978年2015年までの埼玉の気温情報を取得
		GetTemprature getTemprature = new GetTemprature(precNo, blockNo);
		StoreData store = new StoreData();
		for(int i=startYear;i<=endYear;i++){
			store.outputCsv("./data/"+i+".csv", getTemprature.getTemp(i));

			System.out.println( i + ":GET");
		}
		System.out.println(System.currentTimeMillis()-start);
	}

	// connect array
	static double[][] connectArray(double[][] data1, double[][] data2){
		double[][]returnData =null;
		if(data1[0].length== data2[0].length){//配列１と配列２の要素数を比較
			int len = data1.length+data2.length;
			returnData = new double[len][data1[0].length];

			//配列１を挿入
			for(int i=0; i<data1.length;i++){
				for(int j=0;j<data1[i].length;j++){
					returnData[i][j] = data1[i][j];
				}
			}

			//配列２を挿入
			for(int i=0;i<data2.length;i++){
				for(int j=0;j<data2[i].length;j++){
					returnData[i+data1.length][j] = data2[i][j];
				}
			}
		}else{
			System.out.println("differ array amount.");
		}
		return returnData;
	}
}
