function PhotoAPI() {}

PhotoAPI.URL = APIConfig.ENDPOINT;

PhotoAPI.getAllPhotos = function(page) {
    var endpoint = PhotoAPI.URL+"photo?page="+page+"&size=30";
//    var parameters = page;
	var settings = {
        url: endpoint, 
        async: true
    };
    return Promise.resolve($.ajax(settings));
};

PhotoAPI.getPhotosWithoutAlbum = function(page) {
    var endpoint = "photo/withoutAlbum?page="+page+"&size=30";
	var settings = {
        url: endpoint,
        dataType: "json",
        contentType: "application/json",
        async: true
    };
    return Promise.resolve($.ajax(settings));
}

PhotoAPI.save = function(photoId, name, description, originalFilename, thumbnailUri, uri, width, height, size, dateTaken, updated, callback) {
    var endpoint = "photo/";
    var requestMethod = "POST";
    if(photoId != null) {
        requestMethod = "PUT";
    }
	var settings = {
        url: endpoint,
        data: "",
        dataType: "json",
        contentType: "application/json",
        processData: false,
        method: requestMethod,
        async: true
    };
    return Promise.resolve($.ajax(settings));
};
