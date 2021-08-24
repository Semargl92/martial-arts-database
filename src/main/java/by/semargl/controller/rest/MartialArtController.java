package by.semargl.controller.rest;

import java.util.List;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import by.semargl.controller.requests.MartialArtRequest;
import by.semargl.domain.MartialArt;
import by.semargl.service.MartialArtService;

@RestController
@RequestMapping("/martial_art")
@RequiredArgsConstructor
public class MartialArtController {

    private final MartialArtService martialArtService;

    @ApiOperation(value = "find all martial arts")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Martial arts were successfully found")
    })
    @GetMapping
    public List<MartialArt> findAll() {
        return martialArtService.findAllMartialArt();
    }

    @ApiOperation(value = "find one martial art")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "martialArtId", dataType = "string", paramType = "path",
                    value = "id of martial art for search", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
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
                    value = "id of martial art for deleting from database", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Martial art was successfully deleted"),
            @ApiResponse(code = 500, message = "There is no martial art with such id")
    })
    @DeleteMapping("/admin/{martialArtId}")
    public void delete(@PathVariable("martialArtId") Long id) {
       martialArtService.deleteMartialArt(id);
    }

    @ApiOperation(value = "create one martial art")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Martial art was successfully created")
    })
    @PostMapping("/admin")
    public MartialArt create(@RequestBody MartialArtRequest martialArtRequest) {
        return martialArtService.createMartialArt(martialArtRequest);
    }

    @ApiOperation(value = "update one martial art")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "martialArtId", dataType = "string", paramType = "path",
                    value = "id of martial art for update", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Martial art was successfully updated"),
            @ApiResponse(code = 500, message = "There is no martial art with such id")
    })
    @PutMapping("/admin/{martialArtId}")
    public MartialArt update(@PathVariable("martialArtId") Long id, @RequestBody MartialArtRequest martialArtRequest) {
        return martialArtService.updateMartialArt(id,martialArtRequest);
    }

    @ApiOperation(value = "find martial arts by country")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "countryName", dataType = "string", paramType = "query",
                    value = "name of country of martial art for search", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Martial arts were successfully found"),
            @ApiResponse(code = 500, message = "There is no martial art with such origin")
    })
    @GetMapping("/origin")
    public List<MartialArt> findByCountry(@RequestParam String countryName) {
        return martialArtService.findMartialArtsByOrigin(countryName);
    }
}
