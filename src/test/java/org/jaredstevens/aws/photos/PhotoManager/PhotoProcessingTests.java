/**
 * Copyright (c) Jared Stevens 2017 All Rights Reserved.
 */
package org.jaredstevens.aws.photos.PhotoManager;

import com.google.common.io.ByteSource;
import org.jaredstevens.aws.photos.PhotoManager.photo.PhotoProcessing;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
/**
 * @author jared
 */
public class PhotoProcessingTests {
	@Test
	public void getHashTest() throws IOException, InterruptedException {
		ByteSource byteSource = ByteSource.wrap(new String("Hi there.").getBytes());
		String hash1 = PhotoProcessing.getHash(byteSource);
		Thread.sleep(300);
		System.out.println("Hash: "+hash1);
		String hash2 = PhotoProcessing.getHash(byteSource);
		assertEquals(hash1, hash2);
	}

}
