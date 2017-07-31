var page = 0;
var selectMode = false;
PhotoAPI.getAllPhotos(page, getAllResponse_new);

function getAllResponse(result) {
    console.log("Received list of "+result.length+" pictures from server.");
    var container = $("#container");
    for(var index in result) {
        var image = $(document.createElement('img'));
        image.attr("src", "/thumbnail/"+result[index].photoId);
        image.addClass("item");
        // Don't use these -- these properties contain the full image size,
        // not the thumbnail
        // image.attr("width", result[index].width);
        // image.attr("height", result[index].height);
        image.attr("width", 256);
        image.attr("height", 170);
        container.append(image);
    }
    console.log("Done.");
}

function getAllResponse_new(result) {
    console.log("Received list of "+result.length+" pictures from server.");
    var container = $("#container");
    for(var index in result) {
        var imageOption = $(document.createElement('option'));
        imageOption.attr("data-img-src", "/thumbnail/"+result[index].photoId);
        imageOption.attr("value", result[index].photoId);
        imageOption.addClass("item");
        container.append(imageOption);
    }
    $("#container").imagepicker();
    console.log("Done.");
}

function selectPhotos(target) {
    // Toggle select mode
    if(selectMode) {
        selectMode = false;
        $("#container").imagepicker({limit:0});
    } else {
        selectMode = true;
        $("#container").imagepicker({limit:10});
    }
}

function createAlbum(target) {
    var form = $("#albumProperties");
    var selectedItems = $("#container").find(":selected");
    var selectedIds = Array();
    for(var i = 0; i < selectedItems.length; ++i) {
        // The value of each <option> element is the photoId
        selectedIds.push(selectedItems[i].value);
    }
    console.log("Created array of "+selectedIds.length+" elements.");
    var savePromise = AlbumAPI.save(
        null,
        $("#name").val(),
        $("#description").val(),
        function(response) {
            console.log("Album created.")
        }
    );
    savePromise.then(function(result) {
        var albumId = 0;
        for(var i = 0; i < selectedIds.length; ++i) {
            albumId = result.albumId;
            AlbumAPI.addPhoto(albumId, selectedIds[i], function(result) {
               console.log("Added photo "+selectedIds[i]+" to album "+albumId); 
            });
        }
    }, function(err) {
        console.log("There was a problem while creating the Album.");
        console.log(err);
    });
}

function selectImage(target) {
    console.log("You clicked a " + target.nodeName + " object");
}

function doNothing(target) {
    console.log("Doing nothing.");
}

$(function() {
    $("#container").imagepicker();
//    var wall = new Freewall("#newContainer");
//    var wall = new Freewall("#container");
//    wall.fitWidth();
});

$(window).scroll(function() {
    if($(window).scrollTop() + $(window).height() == $(document).height()) {
        ++page;
        PhotoAPI.getAllPhotos(page, getAllResponse_new);
    }
});
