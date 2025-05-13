package org.example;

import org.rocksdb.*;

public class RocksDBMemoryAnalysis {
  public static void main(String[] args) throws Exception {

    System.out.println("Before create RocksDB instance. Press anything to enter...");
    System.in.read();

    RocksDB.loadLibrary();

    Options options = new Options()
        .setCreateIfMissing(true)
        .setWriteBufferSize(64 * 1024 * 1024) // 64MB
        .setMaxWriteBufferNumber(3)
        .setMaxBackgroundCompactions(2);

    String path = "/tmp/rocksdb_test";

    RocksDB db = RocksDB.open(options, path);

    long blockCacheUsage   = db.getLongProperty("rocksdb.block-cache-usage");
    long indexFilterUsage  = db.getLongProperty("rocksdb.estimate-table-readers-mem");
    long memTablesUsage    = db.getLongProperty("rocksdb.cur-size-all-mem-tables");

    System.out.printf("BlockCache: %.2f MB\n", blockCacheUsage/1024.0/1024.0);
    System.out.printf("Index/Filter Cache: %.2f MB\n", indexFilterUsage/1024.0/1024.0);
    System.out.printf("MemTables: %.2f MB\n", memTablesUsage/1024.0/1024.0);

    System.out.println("Before put kv into RocksDB instance. Press anything to continue...");
    System.in.read();

    byte[] key = new byte[128];
    byte[] value = new byte[1024]; // 每条记录 1KB

    for (int i = 0; i < 100_000; i++) {
      key[0] = (byte) (i & 0xFF);
      db.put(key, value);
    }

    System.out.println("Finished writing. Press anything to exit...");
    System.in.read();

    blockCacheUsage   = db.getLongProperty("rocksdb.block-cache-usage");
    indexFilterUsage  = db.getLongProperty("rocksdb.estimate-table-readers-mem");
    memTablesUsage    = db.getLongProperty("rocksdb.cur-size-all-mem-tables");

    System.out.printf("BlockCache: %.2f MB\n", blockCacheUsage/1024.0/1024.0);
    System.out.printf("Index/Filter Cache: %.2f MB\n", indexFilterUsage/1024.0/1024.0);
    System.out.printf("MemTables: %.2f MB\n", memTablesUsage/1024.0/1024.0);

    db.close();

    options.close();
  }
}
