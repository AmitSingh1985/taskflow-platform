
# Git Workflow

## Purpose

This document defines the Git branching strategy, commit conventions, and pull request workflow used in the TaskFlow Platform project.

The objective is to maintain a clean commit history, enable parallel feature development, and ensure that all changes are reviewed and tested before merging into the main branch.

---

# Branching Strategy

The project follows a simplified Git Flow approach.

## Main Branches

### main

- Production-ready code
- Always stable
- Protected branch
- Tagged with release versions

Example:

```
main
```

---

### develop

- Integration branch for ongoing development
- All completed features are merged here first
- Serves as the base branch for feature development

Example:

```
develop
```

---

# Supporting Branches

## Feature Branches

Used for developing a single feature or enhancement.

Naming Convention

```
feature/<feature-name>
```

Examples

```
feature/authentication

feature/user-registration

feature/project-management

feature/task-comments

feature/email-notifications
```

Feature branches are created from `develop` and merged back into `develop`.

---

## Bug Fix Branches

Used for fixing defects discovered during development.

Naming Convention

```
bugfix/<issue-name>
```

Examples

```
bugfix/jwt-expiration

bugfix/task-validation

bugfix/login-error
```

---

## Hotfix Branches

Used to fix production issues.

Created directly from `main`.

Naming Convention

```
hotfix/<issue-name>
```

Examples

```
hotfix/security-patch

hotfix/token-refresh
```

After completion, hotfixes are merged into both:

- main
- develop

---

## Release Branches

Used to prepare production releases.

Naming Convention

```
release/<version>
```

Examples

```
release/v1.0.0

release/v1.1.0
```

---

# Branch Workflow

```
main
  ▲
  │
release/*
  ▲
  │
develop
  ▲
  │
feature/*
```

---

# Commit Message Convention

The project follows the Conventional Commits specification.

Format

```
<type>(scope): <description>
```

Example

```
feat(auth): implement JWT authentication

fix(task): resolve status update issue

docs: add architecture documentation

test(user): add integration tests

refactor(project): simplify validation logic

chore: update dependencies
```

---

# Commit Types

| Type | Purpose |
|--------|----------|
| feat | New feature |
| fix | Bug fix |
| docs | Documentation |
| refactor | Internal code improvement |
| test | Tests |
| chore | Maintenance |
| build | Build configuration |
| ci | CI/CD changes |
| perf | Performance improvements |
| style | Formatting only |

---

# Pull Request Process

Every feature should be merged through a Pull Request.

Checklist before creating a PR:

- Code compiles successfully
- Tests pass
- Code formatted
- No unnecessary files committed
- Documentation updated if required
- Meaningful commit messages
- Reviewer comments addressed

---

# Merge Strategy

Preferred merge strategy:

```
Squash and Merge
```

Benefits:

- Cleaner Git history
- One commit per feature
- Easier rollback
- Better readability

---

# Tagging Strategy

Production releases are tagged.

Examples

```
v1.0.0

v1.1.0

v2.0.0
```

Semantic Versioning is followed.

```
MAJOR.MINOR.PATCH
```

Example

```
2.3.5
│ │ └── Patch
│ └──── Minor
└────── Major
```

---

# Code Review Guidelines

Every Pull Request should be reviewed for:

- Readability
- Naming conventions
- SOLID principles
- Error handling
- Logging
- Security
- Test coverage
- Documentation updates

---

# Git Best Practices

- Commit frequently with meaningful messages.
- Keep commits focused on a single logical change.
- Avoid committing generated files or IDE-specific configuration.
- Rebase feature branches with `develop` regularly to reduce merge conflicts.
- Delete feature branches after they are merged.
- Never rewrite the history of the `main` branch.

---

# Example Workflow

Create a feature branch

```
git checkout develop

git pull origin develop

git checkout -b feature/task-assignment
```

Commit changes

```
git add .

git commit -m "feat(task): implement task assignment"
```

Push branch

```
git push origin feature/task-assignment
```

Create a Pull Request

```
feature/task-assignment

↓

develop
```

After review and successful CI checks, squash and merge into `develop`.

---

# Future Enhancements

As the project evolves, the Git workflow will be enhanced with:

- Branch protection rules
- Required status checks
- Automated code quality analysis
- Dependency vulnerability scanning
- Automated release creation
- Conventional Commit validation
- Semantic version automation
