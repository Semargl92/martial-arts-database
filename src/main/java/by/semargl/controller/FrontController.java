package by.semargl.controller;

import javax.servlet.http.HttpServlet;

//@Controller
//@RestController
public class FrontController extends HttpServlet {

  /*  @Autowired
    public UserRepository userRepository;

    public FrontController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processGetRequests(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processPostRequests(req, resp);
    }

    private void processGetRequests(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Commands commandName = Commands.findByCommandName(req.getParameter("command"));
        try {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/bye");
            if (dispatcher != null) {
                resolveGetRequestCommands(req, commandName);
                dispatcher.forward(req, resp);
            }
        } catch (Exception e) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/error");
            if (dispatcher != null) {
                req.setAttribute("trace", e.getMessage());
                dispatcher.forward(req, resp);
            }
        }
    }


   /* private void resolveGetRequestCommands(HttpServletRequest req, Commands commandName) {

        //http://localhost:8080/test/FrontController?command=findAll&page=0&limit=10 (add offset to query)

        switch (commandName) {
            //     http://localhost:8080/test/FrontController?command=findAll
            case FIND_ALL:
                String page = req.getParameter("page");
                String limit = req.getParameter("limit");

                System.out.println(page);
                System.out.println(limit);

                req.setAttribute("users", userRepository.findAll());
                break;
            //     http://localhost:8080/test/FrontController?command=findById&id=10
            case FIND_BY_ID:
                String id = req.getParameter("id");
                long userId = Long.parseLong(id);
                req.setAttribute("users", Collections.singletonList(userRepository.findOne(userId)));
                req.setAttribute("singleUser", userRepository.findOne(userId));
                break;
            default:
                break;

        }
    }

    private void processPostRequests(HttpServletRequest req, HttpServletResponse resp) {
        Commands commandName = Commands.findByCommandName(req.getParameter("command"));
        try {
            switch (commandName) {
                case CREATE:
                    //Deserializing post request body to User class object
                    String body = IOUtils.toString(req.getInputStream(), Charset.defaultCharset());
                    User user = new Gson().fromJson(body, User.class);
                    //Saving user object
                    req.setAttribute("users", Collections.singletonList(userRepository.save(user)));
                    break;
                case UPDATE:
                    String updateBody = IOUtils.toString(req.getInputStream(), Charset.defaultCharset());
                    User updateUser = new Gson().fromJson(updateBody, User.class);
                    req.setAttribute("users", Collections.singletonList(userRepository.update(updateUser)));
                    break;
                case DELETE:
                    String id = req.getParameter("id");
                    long userId = Long.parseLong(id);
                    //userRepository.delete(userRepository.findOne(userId));

                    req.setAttribute("users", userRepository.findAll());
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    } */
}
