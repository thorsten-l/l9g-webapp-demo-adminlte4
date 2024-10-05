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
package l9g.webapp.demo.adminlte4.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author Thorsten Ludewig <t.ludewig@gmail.com>
 */
@Component
@Slf4j
public class CsvDb
{
  public static final String DEMO_PERSONS_FILENAME = "demo-persons.csv";

  public static final int DEMO_PERSONS_COLUMNS = 9;

  public static final int DEMO_PERSONS_COLUMN_FIRSTNAME = 2;

  public static final int DEMO_PERSONS_COLUMN_LASTNAME = 3;

  public static final int DEMO_PERSONS_COLUMN_CITY = 5;

  public static final int DEMO_PERSONS_COLUMN_EMAIL = 7;

  @EventListener(ApplicationReadyEvent.class)
  public void onStartup()
  {
    log.info("onStartup");
    int numberOfPersons = 0;
    try
    {
      BufferedReader reader = new BufferedReader(new FileReader(
        DEMO_PERSONS_FILENAME));
      String line;
      while ((line = reader.readLine()) != null)
      {
        line = line.trim();
        String columns[] = line.split("\\;");
        if (columns.length == DEMO_PERSONS_COLUMNS)
        {
          DbPerson person = new DbPerson(
            UUID.randomUUID().toString(),
            columns[DEMO_PERSONS_COLUMN_FIRSTNAME],
            columns[DEMO_PERSONS_COLUMN_LASTNAME],
            columns[DEMO_PERSONS_COLUMN_CITY],
            columns[DEMO_PERSONS_COLUMN_EMAIL]);
          persons.put(person.uuid(), person);
          numberOfPersons++;
        }
      }
    }
    catch (Throwable t)
    {
      log.error("Initialization error : {}", t);
      System.exit(-1);
    }

    persons = persons.entrySet().stream()
      .sorted(Map.Entry.comparingByValue(
        Comparator.comparing(DbPerson::lastname)
          .thenComparing(DbPerson::firstname)))
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
        (e1, e2) -> e1, LinkedHashMap::new
      ));

    log.debug("{} persons imported.", numberOfPersons);
  }

  public List<DbPerson> findPersonByName(
    int skip, int limit, final String term)
  {
    final String lowerTerm = term.toLowerCase();

    if (term.trim().length() == 0)
    {
      return persons.values()
        .stream()
        .skip(skip)
        .limit(limit)
        .collect(Collectors.toList());
    }
    else
    {
      return persons.values()
        .stream()
        .filter(p ->
        {
          return p.firstname().toLowerCase().startsWith(lowerTerm)
            || p.lastname().toLowerCase().startsWith(lowerTerm);
        })
        .skip(skip)
        .limit(limit)
        .collect(Collectors.toList());
    }
  }

  public DbPerson findById( String uuid )
  {
    return persons.get(uuid);
  }
  
  private LinkedHashMap<String, DbPerson> persons = new LinkedHashMap<>();
}
