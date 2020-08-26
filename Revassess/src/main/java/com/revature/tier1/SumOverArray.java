package com.revature.tier1;

public class SumOverArray {

	public static int IterateAndSum(int[] arr) {
		int sum=0;
		
		if(arr == null) {
			sum = 0;
		}else{
			for(int a : arr) {
				sum+=a;	
			}
		}
		return sum;
	}
}
