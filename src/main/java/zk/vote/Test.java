package zk.vote;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WeiYang on 2018/12/29.
 *
 * @Author: WeiYang
 * @Package zk.vote
 * @Project: zookeeper_demos
 * @Title:
 * @Description: Please fill description of the file here
 * @Date: 2018/12/29 10:10
 */
public class Test {

    private static final int CLIENT_QTY = 10;

    private static final String SERVER = "47.96.233.184:2181";

    public static void main(String[] args) throws Exception {
        List<ZkClient> clients = new ArrayList<>();

        List<WorkClient> workClients = new ArrayList<>();

        try {
            for (int i = 0; i< CLIENT_QTY; ++i) {
                ZkClient client = new ZkClient(SERVER, 5000, 5000, new SerializableSerializer());
                clients.add(client);

                WorkClient workClient = new WorkClient(client, "Client #" + i);

                workClients.add(workClient);
                workClient.start();
            }
            System.out.println("敲回车键退出！\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Shutting down...");
            for (WorkClient client : workClients) {
                client.stop();
            }

            for (ZkClient client : clients) {
                client.close();
            }
        }
    }


}
