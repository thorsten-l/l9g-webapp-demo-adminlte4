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

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Thorsten Ludewig <t.ludewig@gmail.com>
 */
@Controller
@Slf4j
public class ToastSamplesController
{
  @GetMapping("/toast/primary")
  public String toastPrimary(RedirectAttributes redirectAttributes)
  {
    redirectAttributes.addFlashAttribute("toast", new Toast("Primary",
      "Primary toast", " text-bg-primary"));
    return "redirect:/ui/forms/toasts";
  }

  @GetMapping("/toast/success")
  public String toastSuccess(RedirectAttributes redirectAttributes)
  {
    redirectAttributes.addFlashAttribute("toast", new Toast("Success",
      "Your operation succeed.", " text-bg-success"));
    return "redirect:/ui/forms/toasts";
  }

  @GetMapping("/toast/info")
  public String toastInfo(RedirectAttributes redirectAttributes)
  {
    redirectAttributes.addFlashAttribute("toast", new Toast("Info",
      "Lorem ipsum...", " text-bg-info"));
    return "redirect:/ui/forms/toasts";
  }

  @GetMapping("/toast/warning")
  public String toastWarning(RedirectAttributes redirectAttributes)
  {
    redirectAttributes.addFlashAttribute("toast", new Toast("Warning",
      "Something might be strange.", " text-bg-warning"));
    return "redirect:/ui/forms/toasts";
  }

  @GetMapping("/toast/danger")
  public String toastDanger(RedirectAttributes redirectAttributes)
  {
    redirectAttributes.addFlashAttribute("toast", new Toast("Danger",
      "Danger dang.. da... i need a break.", " text-bg-danger"));
    return "redirect:/ui/forms/toasts";
  }
}
