function PhotoAPI() {}

PhotoAPI.URL = APIConfig.ENDPOINT;

PhotoAPI.getAllPhotos = function(page, callback) {
    var method = "";
//    var parameters = page;
	var settings = {url: PhotoAPI.URL+method+"?page="+page+"&size=30", success:callback, async: true};
    $.ajax(settings);
};

PhotoAPI.save = function(name, description, originalFilename, thumbnailUri, uri, width, height, size, dateTaken, updated, callback) {
    var method = "/";
    var parameters = page;
	var settings = {url: PhotoAPI.URL+method+parameters, success:callback, async: true};
    $.ajax(settings);
};

PhotoAPI.update = function(photoId, name, description, originalFilename, thumbnailUri, uri, width, height, size, dateTaken, updated, callback) {
    var method = "/";
    var parameters = page;
	var settings = {
        url: PhotoAPI.URL+method+parameters,
        data: {
            "photoId":photoId,
            "name":name,
            "description":description,
            "originalFilename":originalFilename,
            "thumbnailUri":thumbnailUri,
            "uri":uri,
            "width":width,
            "height":height,
            "size":size,
            "dateTaken":dateTaken,
            "updated":updated
        },
        dataType: "json",
        method: "PUT",
        processData: false,
        success:callback, async: true
    };
    $.ajax(settings);
};