//TODO
//@Controller
//public class LocaleController {
//    @PostMapping("/locate")
//    public String changeLocale(@RequestParam("lang") String language,
//                               @RequestHeader(value = "Referer", required = false) String referer,
//                               HttpSession session,
//                               RedirectAttributes redirectAttributes,
//                               @AuthenticationPrincipal UserDetails userDetails) {
//        session.setAttribute("lang", language);
//
//        String redirectUrl = (referer != null) ? referer : "/login";
//        redirectAttributes.addAttribute("lang", language);
//
//        return "redirect:" + redirectUrl;
//    }
//}
