
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


class Httpcom{
	boolean debag=false;
	HttpURLConnection conn;

	void openWeb(String Url){//open web page
		try {
			URL uri=new URL(Url);//create uri
			conn =(HttpURLConnection)uri.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();

//			if(debag)
//				System.out.println(conn.getResponseCode());//get response code... ex)200, connection is successful

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	void closeWeb(){//close web page
		conn.disconnect();
	}

	//get web page
	List getWeb(String url){
		openWeb(url);
		List dataList = new ArrayList();
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String tmp;
			while((tmp = br.readLine()) !=null){
				if(debag)
					System.out.println(tmp);

				dataList.add(tmp);
			}
			closeWeb();
			br.close();

		}catch(Exception e){
			e.printStackTrace();
		}

		return dataList;
	}
}