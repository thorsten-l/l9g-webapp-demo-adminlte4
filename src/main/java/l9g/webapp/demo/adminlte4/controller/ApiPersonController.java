/*
 * Copyright 2024 Thorsten Ludewig <t.ludewig@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package l9g.webapp.demo.adminlte4.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import l9g.webapp.demo.adminlte4.db.CsvDb;
import l9g.webapp.demo.adminlte4.db.DbPerson;
import l9g.webapp.demo.adminlte4.dto.DtoSelect2Entry;
import l9g.webapp.demo.adminlte4.dto.DtoSelect2Pagination;
import l9g.webapp.demo.adminlte4.dto.DtoSelect2Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Thorsten Ludewig <t.ludewig@gmail.com>
 */
@Slf4j
@RequiredArgsConstructor
@RestController()
@RequestMapping(path = "/api/v1/persons",
                produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiPersonController
{
  private final CsvDb db;

  @GetMapping
  public DtoSelect2Result findPersons(
    @RequestParam(required = false, defaultValue = "1") int page,
    @RequestParam(required = false, defaultValue = "") String term,
    @RequestParam(name = "_type", required = false, defaultValue = "") String type
  )
  {
    log.debug("findPersons page={} term='{}' type='{}'", page, term, type);

    List<DbPerson> personsPage = db.findPersonByName((page - 1) * 10, 10, term);
    ArrayList<DtoSelect2Entry> entries = new ArrayList<>();

    personsPage.forEach((DbPerson p)
      -> entries.add(new DtoSelect2Entry(
        p.uuid(), p.lastname() + ", " + p.firstname() + ", " + p.email(), false)
      ));

    DtoSelect2Pagination pagination
      = new DtoSelect2Pagination(entries.size() == 10);
    DtoSelect2Result result = new DtoSelect2Result(entries, pagination);
    return result;
  }

  @PostMapping
  public ResponseEntity<Void> submitPersons(
    @RequestParam("selectedPerson") List<String> selectedPersonIds)
  {
    selectedPersonIds.stream()
      .map(db::findById)
      .forEach(System.out::println);

    // Redirect to '/ui/forms/select2'
    URI redirectUri = ServletUriComponentsBuilder.fromCurrentContextPath()
      .path("/ui/forms/select2")
      .build()
      .toUri();

    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(redirectUri);

    return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
  }
}
