package by.semargl.controller.rest;

import by.semargl.controller.requests.MartialArtRequest;
import by.semargl.domain.MartialArt;
import by.semargl.service.MartialArtService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/martial_art")
@RequiredArgsConstructor
public class MartialArtController {

    private final MartialArtService martialArtService;

    @ApiOperation(value = "find all martial arts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Martial arts were successfully found")
    })
    @GetMapping("/all")
    public List<MartialArt> findAll() {
        return martialArtService.findAllMartialArt();
    }

    @ApiOperation(value = "find one martial art")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "martialArtId", dataType = "string", paramType = "path",
                    value = "id of martial art for search", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Martial art was successfully found"),
            @ApiResponse(code = 500, message = "There is no martial art with such id")
    })
    @GetMapping("/{martialArtId}")
    public MartialArt findOne(@PathVariable("martialArtId") Long id) {
        return martialArtService.findOneMartialArt(id);
    }

    @ApiOperation(value = "remove martial art from the database")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "martialArtId", dataType = "string", paramType = "path",
                    value = "id of martial art for deleting from database", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Martial art was successfully deleted"),
            @ApiResponse(code = 500, message = "There is no martial art with such id")
    })
    @DeleteMapping("/delete/{martialArtId}")
    public void delete(@PathVariable("martialArtId") Long id) {
       martialArtService.deleteMartialArt(id);
    }

    @ApiOperation(value = "create one martial art")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Martial art was successfully created")
    })
    @PostMapping("/create")
    public MartialArt create(@RequestBody MartialArtRequest martialArtRequest) {
        return martialArtService.createMartialArt(martialArtRequest);
    }

    @ApiOperation(value = "update one martial art")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "martialArtId", dataType = "string", paramType = "path",
                    value = "id of martial art for update", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Martial art was successfully updated"),
            @ApiResponse(code = 500, message = "There is no martial art with such id")
    })
    @PutMapping("/update/{martialArtId}")
    public MartialArt update(@PathVariable("martialArtId") Long id, @RequestBody MartialArtRequest martialArtRequest) {
        return martialArtService.updateMartialArt(id,martialArtRequest);
    }
}
