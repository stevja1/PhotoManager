function AlbumAPI() {}

AlbumAPI.URL = APIConfig.ENDPOINT;

AlbumAPI.save = function(id, name, description) {
    var endpoint = AlbumAPI.URL + "album";
    var albumId = null;
    var requestMethod = "POST";
    if(id != -1) {
        albumId = id;
        method = "PUT";
    }
	var settings = {
        url: endpoint,
        data: "{\"albumId\":\""+id+"\",\"name\":\""+name+"\",\"description\":\""+description+"\"}",
        method: requestMethod,
        dataType: "json",
        contentType: "application/json",
        processData: false,
        async: true
    };

    return Promise.resolve($.ajax(settings));
};

AlbumAPI.addPhoto = function(albumId, photoId) {
    var endpoint = AlbumAPI.URL + "album/addPhoto/" + albumId + "/" + photoId;
    var requestMethod = "PUT";
	var settings = {
        url: endpoint,
        method: requestMethod,
        async: true
    };

    return Promise.resolve($.ajax(settings));
};

AlbumAPI.addPhotos = function(albumId, photoIds) {
    var endpoint = AlbumAPI.URL + "album/addPhotos/" + albumId;
    var requestMethod = "PUT";
    var photoIdList = "[";
    for(var i = 0; i < photoIds.length; ++i) {
        photoIdList += photoIds[i] + ",";
    }
    photoIdList = photoIdList.substr(0, photoIdList.length - 1);
    photoIdList += "]";
    
	var settings = {
        url: endpoint,
        method: requestMethod,
        dataType: "xml text",
        contentType: "application/json",
        data: photoIdList,
        processData: false,
        async: true
    };

    return Promise.resolve($.ajax(settings));
};

AlbumAPI.removePhoto = function(albumId, photoId) {
    var endpoint = AlbumAPI.URL + "album/removePhoto/" + albumId + "/" + photoId;
    var requestMethod = "PUT";
	var settings = {
        url: endpoint,
        method: requestMethod,
        dataType: "json",
        contentType: "application/json",
        async: true
    };

    return Promise.resolve($.ajax(settings));
};

AlbumAPI.delete = function(albumId) {
    var endpoint = AlbumAPI.URL + "album/"+albumId;
	var settings = {url: endpoint, method: "DELETE", async: true};
    return Promise.resolve($.ajax(settings));
};

AlbumAPI.get = function(albumId) {
    var endpoint = AlbumAPI.URL + "album/"+albumId;
	var settings = {
        url: endpoint,
        dataType: "json",
        contentType: "application/json",
        async: true
    };
    return Promise.resolve($.ajax(settings));
};

AlbumAPI.getAll = function() {
    var endpoint = AlbumAPI.URL + "album";
	var settings = {
        url: endpoint,
        dataType: "json",
        contentType: "application/json",
        async: true
    };
    return Promise.resolve($.ajax(settings));
};
