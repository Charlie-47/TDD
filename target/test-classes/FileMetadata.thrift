
enum FormatType {
    GENERIC,
    PARQUET
}

struct FileMetadata {
    1: required FormatType file_format,
    2: optional i64 record_count,
    3: optional i64 file_size_in_bytes,
    4: optional map<i32, i64> column_sizes,
    5: optional map<i32, i64> value_counts,
    6: optional map<i32, i64> null_value_counts,
    7: optional map<i32, i64> nan_value_counts,
    8: optional map<i32, binary> lower_bounds,
    9: optional map<i32, binary> upper_bounds,
    10: optional binary key_metadata,
    11: optional list<i64> split_offsets,
    12: optional i32 sort_order_id
}

service MetadataService {
    void Ping()

    FileMetadata getFileMetadata(1: string dbname, 2: string tblname, 3: string filename)
    void putFileMetadata(1: string dbname, 2: string tblname, 3: string filename, 4: FileMetadata fileMetadata)
    void deleteFileMetadata(1: string dbname, 2: string tblname, 3: string filename)

    map<string, FileMetadata> getFileMetadataBatch(1: string dbname, 2: string tblname, 3: list<string> filenames)
    void putFileMetadataBatch(1: string dbname, 2: string tblname, 3: list<string> filenames, 4: list<FileMetadata> files)
}