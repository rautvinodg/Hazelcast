package com.example.demo.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.scheduledexecutor.DuplicateTaskException;
import com.hazelcast.scheduledexecutor.IScheduledExecutorService;
import com.hazelcast.scheduledexecutor.IScheduledFuture;
import com.hazelcast.scheduledexecutor.TaskUtils;

@Configuration
public class MainConfig {

	public static String clusterName = "MyCluster";
	public static String taskName = "MyTask";
	
	@Bean
	public HazelcastInstance HazelCast() {
				
		Config config = new Config();
		
		/**
		*	We need to use same cluster name in all the instances
		*	so instances on same or different nodes will be part of
		*	same cluster. 
		*/
		config.setClusterName(clusterName);
		config.setInstanceName(clusterName);

        	NetworkConfig network = config.getNetworkConfig();
		JoinConfig join = network.getJoin();
		join.getMulticastConfig().setEnabled(false);
		join.getTcpIpConfig().setEnabled(true);
		join.getTcpIpConfig().addMember("127.0.0.1");
		
		HazelcastInstance hz = Hazelcast.newHazelcastInstance(config);
		if (null == hz) {
			return hz;
		}
		
		try {
        IScheduledExecutorService es = hz.getScheduledExecutorService ("default");
		
		/**
		*	Having same task name in the cluster will restrict the multiple tasks
		*	in the cluster and execution will be single instance
		*	when 1st instance will execute below code the task will be created
		*	further instances will not be able to register same name tasks
		*	and the will have ‘DuplicateTaskException’ error 
		*/
		IScheduledFuture <?> Future = es.scheduleAtFixedRate(
				TaskUtils.named(taskName, new HelloTask()),
				5, 30, TimeUnit.SECONDS);
		} catch (DuplicateTaskException e) {
			System.out.println("Already scheduled task");
		}
		return hz;
	}

}
