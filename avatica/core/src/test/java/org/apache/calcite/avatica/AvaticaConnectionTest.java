/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.calcite.avatica;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Properties;

/**
 * Tests for AvaticaConnection
 */
public class AvaticaConnectionTest {

  @Test
  public void testNumExecuteRetries() {
    // Disabled on JDK9 due to Mockito bug; see [CALCITE-1567].
    Assume.assumeTrue(System.getProperty("java.version").compareTo("9") < 0);

    AvaticaConnection statement = Mockito.mock(AvaticaConnection.class);

    Mockito.when(statement.getNumStatementRetries(Mockito.any(Properties.class)))
      .thenCallRealMethod();

    // Bad argument should throw an exception
    try {
      statement.getNumStatementRetries(null);
      Assert.fail("Calling getNumStatementRetries with a null object should throw an exception");
    } catch (NullPointerException e) {
      // Pass
    }

    Properties props = new Properties();

    // Verify the default value
    Assert.assertEquals(Long.valueOf(AvaticaConnection.NUM_EXECUTE_RETRIES_DEFAULT).longValue(),
        statement.getNumStatementRetries(props));

    // Set a non-default value
    props.setProperty(AvaticaConnection.NUM_EXECUTE_RETRIES_KEY, "10");

    // Verify that we observe that value
    Assert.assertEquals(10, statement.getNumStatementRetries(props));
  }

}

// End AvaticaConnectionTest.java
